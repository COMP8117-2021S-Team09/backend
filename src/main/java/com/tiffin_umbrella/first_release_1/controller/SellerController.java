package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import com.tiffin_umbrella.first_release_1.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SellerController {

    private final SellerRepository sellerRepository;
    private final PlanRepository planRepository;
    private final OrderRepository orderRepository;
    private final MailSenderService mailSenderService;

    @GetMapping(
            value = "/get_seller_list",
            produces = APPLICATION_JSON_VALUE)
    public Collection<SellerEntity> getAllSellers() {
        return sellerRepository.findAll();
    }

    @GetMapping(
            value = "/sellers/{sellerId}/orders",
            produces = APPLICATION_JSON_VALUE)
    public Collection<Order> getOrdersForSeller(
            @PathVariable(name = "sellerId") final String sellerId) {
        return orderRepository.findBySeller_Id(sellerId);
    }

    @PostMapping(
            value = "/get_seller_list",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public List<SellerEntity> get_sellers(
            @RequestBody final SellerEntity filters) {
        Collection<SellerEntity> sellers = sellerRepository.findAll();
        final Status filterStatus = filters.getStatus();
        final Set<Cuisines> filterCuisines = filters.getCuisines();
        final Set<Categories> filterCategories = filters.getCategories();
        return sellers.stream().filter(seller -> (filterStatus == null || seller.getStatus().equals(filterStatus))
                && (filterCuisines == null || seller.getCuisines().containsAll(filterCuisines))
                && (filterCategories == null || seller.getCategories().containsAll(filterCategories)))
                .collect(Collectors.toList());
    }

    @PostMapping(
            value = "/post_seller",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public void post_seller(@RequestBody SellerEntity sellerEntity) {
        planRepository.saveAll(sellerEntity.getPlans());
        sellerRepository.save(sellerEntity);
        mailSenderService.sendRegisterEmail(sellerEntity.getContact().getEmail());
    }

    @GetMapping(
            value = "/get_plans",
            produces = APPLICATION_JSON_VALUE)
    public List<Plan> getSellerPlans(@RequestParam(value = "id") String id) {
        return sellerRepository.findById(id)
                .orElse(SellerEntity.builder().plans(Collections.emptyList()).build())
                .getPlans();
    }

    @PostMapping(value = "/sellers/{sellerId}/plans",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Plan> createPlan(@PathVariable(name = "sellerId") final String sellerId,
                                           @RequestBody final Plan plan) {
        sellerRepository.findById(sellerId).ifPresent(seller -> {
            plan.setId(null);
            plan.setStatus(PlanStatus.AVAILABLE);
            planRepository.save(plan);
            seller.getPlans().add(plan);
            sellerRepository.save(seller);
        });
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}
