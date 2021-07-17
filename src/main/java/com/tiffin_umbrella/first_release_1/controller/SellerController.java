package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import com.tiffin_umbrella.first_release_1.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class SellerController {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MailSenderService mailSenderService;

    @GetMapping("/get_seller_list")
    public List<SellerEntity> get_sellers() {
        return sellerRepository.findAll();
    }

    @GetMapping(value = "/sellers/{sellerId}/orders", produces = APPLICATION_JSON_VALUE)
   	public Collection<Order> getOrdersForSeller(@PathVariable(name = "sellerId") final String sellerId){
    	return orderRepository.findBySeller_Id(sellerId);
    }

    @PostMapping("/get_seller_list")
    public List<SellerEntity> get_sellers(@RequestBody final SellerEntity filters) {
        Collection<SellerEntity> sellers = sellerRepository.findAll();
        final Status filterStatus = filters.getStatus();
        final Set<Cuisines> filterCuisines = filters.getCuisines();
        final Set<Categories> filterCategories = filters.getCategories();
        return sellers.stream().filter(seller -> (filterStatus == null || seller.getStatus().equals(filterStatus))
                && (filterCuisines == null || seller.getCuisines().containsAll(filterCuisines))
                && (filterCategories == null || seller.getCategories().containsAll(filterCategories)))
                .collect(Collectors.toList());
    }

    @PostMapping("/post_seller")
    public void post_seller(@RequestBody SellerEntity sellerEntity) {
        planRepository.saveAll(sellerEntity.getPlans());
        sellerRepository.save(sellerEntity);
        mailSenderService.send_Register_Email(sellerEntity.getContact().getEmail());
    }

    @GetMapping("/get_plans")
    public List<Plan> get_plans(@RequestParam(value = "id") String id) {
        SellerEntity seller = sellerRepository.findById(id).get();
        List<Plan> plans = seller.getPlans();
        return plans;
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
