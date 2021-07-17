package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SellerService {

    private final MailSenderService mailSenderService;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final PlanRepository planRepository;

    public Collection<SellerEntity> findAll() {
        return sellerRepository.findAll();
    }

    public Collection<Order> getOrdersForSeller(final String sellerId) {
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

    public void createSeller(final SellerEntity sellerEntity) {
        planRepository.saveAll(sellerEntity.getPlans());
        sellerRepository.save(sellerEntity);
        mailSenderService.sendRegisterEmail(sellerEntity.getContact().getEmail());
    }

    public Collection<Plan> getSellerPlans(final String sellerId) {
        return sellerRepository.findById(sellerId)
                .orElse(SellerEntity.builder().plans(Collections.emptyList()).build())
                .getPlans();
    }

    public void createSellerPlan(final String sellerId, final Plan plan) {
        sellerRepository.findById(sellerId).ifPresent(seller -> {
            plan.setId(null);
            plan.setStatus(PlanStatus.AVAILABLE);
            planRepository.save(plan);
            seller.getPlans().add(plan);
            sellerRepository.save(seller);
        });
    }
}
