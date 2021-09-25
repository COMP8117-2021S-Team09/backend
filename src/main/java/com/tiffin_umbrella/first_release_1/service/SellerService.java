package com.tiffin_umbrella.first_release_1.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;
import com.tiffin_umbrella.first_release_1.common.BadRequestException;
import com.tiffin_umbrella.first_release_1.common.ErrorCode;
import com.tiffin_umbrella.first_release_1.dto.AddressDto;
import com.tiffin_umbrella.first_release_1.dto.SellerDto;
import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.tiffin_umbrella.first_release_1.common.BadRequestException.throwException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SellerService {

    private final MailSenderService mailSenderService;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final PlanRepository planRepository;

    public Collection<SellerEntity> findAll() {
        return sellerRepository.findAll();
    }

    public Collection<OrderEntity> getOrdersForSeller(final String sellerId) {
        return orderRepository.findBySeller_Id(sellerId);
    }

    public Collection<SellerEntity> getSellersBasedOnFilters(final SellerEntity filters) {
        final Collection<SellerEntity> sellers = sellerRepository.findAll();
        final Status filterStatus = filters.getStatus();
        final Set<Cuisines> filterCuisines = filters.getCuisines();
        final Set<Categories> filterCategories = filters.getCategories();
        return sellers.stream().filter(seller -> (filterStatus == null || seller.getStatus().equals(filterStatus))
                && (filterCuisines == null || seller.getCuisines().containsAll(filterCuisines))
                && (filterCategories == null || seller.getCategories().containsAll(filterCategories)))
                .collect(Collectors.toList());
    }

    public void createSeller(final SellerEntity sellerEntity) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection("crud_user").document(sellerEntity.getName()).set(sellerEntity);
        log.info("apiFuture {}", apiFuture);
//        sellerRepository.findByContact_Email(sellerEntity.getContactEmail())
//                .ifPresent(existing -> throwException(ErrorCode.SELLER_ALREADY_EXISTS_BY_EMAIL));
//        planRepository.saveAll(sellerEntity.getPlans());
//        sellerRepository.save(sellerEntity);
//        mailSenderService.sendRegisterEmail(sellerEntity.getContact().getEmail());
    }

    public Collection<PlanEntity> getSellerPlans(final String sellerId) {
        return sellerRepository.findById(sellerId)
                .orElse(SellerEntity.builder().plans(Collections.emptyList()).build())
                .getPlans();
    }

    public void createSellerPlan(final String sellerId, final PlanEntity plan) {
        sellerRepository.findById(sellerId).ifPresent(seller -> {
            plan.setId(null);
            plan.setStatus(PlanStatus.AVAILABLE);
            planRepository.save(plan);
            seller.getPlans().add(plan);
            updatePlanStatsForSeller(seller, plan);
            sellerRepository.save(seller);
        });
    }

    private void updatePlanStatsForSeller(final SellerEntity seller, final PlanEntity plan) {
        seller.getCategories().add(plan.getCategory());
        seller.getCuisines().add(plan.getCuisine());
        final int numberOfPlans = seller.getPlans().size();
        final double totalPlanPricePerDay = seller.getPlans().stream()
                .flatMapToDouble(planEntity -> DoubleStream.of(planEntity.getPlanPricePerDay()))
                .sum();
        seller.setAveragePricePerPerson(totalPlanPricePerDay / numberOfPlans);
    }

    public void createSeller(final Object sellerDto) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection("crud_user")
                .document(UUID.randomUUID().toString()).set(sellerDto);
        log.info("apiFuture {}", apiFuture);
        log.info("time: {}", apiFuture.get().getUpdateTime().toString());
    }

    @SneakyThrows
    public Object getObject(final String doc_id) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("crud_user").document(doc_id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Object.class);
        }
        return null;
    }

    @SneakyThrows
    public Object updateObject(final String doc_id, final AddressDto address) {
        Firestore firestore = FirestoreClient.getFirestore();
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("state", address.getState());
        hm.put("lat", address.getLatitude());
        hm.put("lng", address.getLongitude());
        hm.put("time", Instant.now());
        final ApiFuture<WriteResult> updated = firestore
                .collection("crud_user").document(doc_id).update(hm);
        log.info("updated : {} with doc_id: {} ", updated, doc_id);
        return updated;
    }

    @SneakyThrows
    public Object deleteObject(final String doc_id) {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleted = firestore.collection("crud_user").document(doc_id).delete();
        log.info("deleted : {} with doc_id: {} ", deleted, doc_id);
        return deleted;
    }
}
