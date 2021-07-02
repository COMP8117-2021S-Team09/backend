package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class SellerController {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    PlanRepository planRepository;

    @GetMapping("/get_seller_list")
    public List<SellerEntity> get_sellers() {
        return sellerRepository.findAll();
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
    }

    @GetMapping("/get_plans")
    public List<Plan> get_plans(@RequestParam(value = "id") String id) {
        SellerEntity seller = sellerRepository.findById(id).get();
        List<Plan> plans = seller.getPlans();
        return plans;
    }
}
