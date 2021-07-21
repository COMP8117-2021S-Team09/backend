package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.adapter.OrderAdapter;
import com.tiffin_umbrella.first_release_1.adapter.PlanAdapter;
import com.tiffin_umbrella.first_release_1.adapter.SellerAdapter;
import com.tiffin_umbrella.first_release_1.dto.OrderDto;
import com.tiffin_umbrella.first_release_1.dto.PlanDto;
import com.tiffin_umbrella.first_release_1.dto.SellerDto;
import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.tiffin_umbrella.first_release_1.adapter.PlanAdapter.adaptForCreation;
import static com.tiffin_umbrella.first_release_1.adapter.PlanAdapter.adaptToDto;
import static com.tiffin_umbrella.first_release_1.adapter.SellerAdapter.adaptCollection;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.*;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class SellerController {

    private final SellerService sellerService;

    @GetMapping(
            value = {"/sellers", "/get_seller_list"},
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SellerDto>> getAllSellers() {
        return new ResponseEntity<>(adaptCollection(sellerService.findAll()), HttpStatus.OK);
    }

    @GetMapping(
            value = "/sellers/{sellerId}/orders",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderDto>> getOrdersForSeller(
            @PathVariable(name = "sellerId") final String sellerId) {
        final Collection<OrderEntity> orders = sellerService.getOrdersForSeller(sellerId);
        return new ResponseEntity<>(OrderAdapter.adaptCollection(orders), HttpStatus.OK);
    }

    @PostMapping(
            value = "/get_seller_list",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SellerDto>> getSellersBasedOnFilters(
            @RequestBody final SellerDto filters) {
        final SellerEntity entityFilters = SellerAdapter.adaptForSearch(filters);
        final Collection<SellerEntity> sellers = sellerService.getSellersBasedOnFilters(entityFilters);
        return new ResponseEntity<>(SellerAdapter.adaptCollection(sellers), HttpStatus.OK);
    }

    @PostMapping(
            value = {"/sellers", "/post_seller"},
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SellerDto> createSeller(
            @RequestBody @Valid SellerDto sellerDto) {
        final SellerEntity sellerEntity = SellerAdapter.adaptForCreation(sellerDto);
        sellerService.createSeller(sellerEntity);
        return new ResponseEntity<>(SellerAdapter.adaptToDto(sellerEntity), HttpStatus.OK);
    }

    @GetMapping(
            value = "/get_plans",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PlanDto>> getSellerPlans(
            @RequestParam(value = "id") String id) {//getPlans is same
        final Collection<PlanEntity> plans = sellerService.getSellerPlans(id);
        return new ResponseEntity<>(PlanAdapter.adaptCollection(plans), HttpStatus.OK);
    }

    @GetMapping(
            value = "/sellers/{sellerId}/plans",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PlanDto>> getPlans(
            @PathVariable(name = "sellerId") final String sellerId) {//getSellerPlans is same
        final Collection<PlanEntity> plans = sellerService.getSellerPlans(sellerId);
        return new ResponseEntity<>(PlanAdapter.adaptCollection(plans), HttpStatus.OK);
    }

    @PostMapping(value = "/sellers/{sellerId}/plans",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanDto> createPlan(
            @PathVariable(name = "sellerId") final String sellerId,
            @RequestBody @Valid final PlanDto plan) {
        final PlanEntity planCreated = adaptForCreation(plan);
        sellerService.createSellerPlan(sellerId, planCreated);
        return new ResponseEntity<>(adaptToDto(planCreated), HttpStatus.OK);
    }
}
