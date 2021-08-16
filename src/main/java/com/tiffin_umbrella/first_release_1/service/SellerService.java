package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.common.BadRequestException;
import com.tiffin_umbrella.first_release_1.common.ErrorCode;
import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.tiffin_umbrella.first_release_1.common.BadRequestException.throwException;

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

    public void createSeller(final SellerEntity sellerEntity) {
        sellerRepository.findByContact_Email(sellerEntity.getContactEmail())
                .ifPresent(existing -> throwException(ErrorCode.SELLER_ALREADY_EXISTS_BY_EMAIL));
        planRepository.saveAll(sellerEntity.getPlans());
        sellerRepository.save(sellerEntity);
        mailSenderService.sendRegisterEmail(sellerEntity.getContact().getEmail());
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
}
