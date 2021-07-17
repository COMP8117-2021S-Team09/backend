package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Collection<SellerEntity>> getAllSellers() {
        return new ResponseEntity<>(sellerService.findAll(), HttpStatus.OK);
    }

    @GetMapping(
            value = "/sellers/{sellerId}/orders",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Order>> getOrdersForSeller(
            @PathVariable(name = "sellerId") final String sellerId) {
        return new ResponseEntity<>(sellerService.getOrdersForSeller(sellerId), HttpStatus.OK);
    }

    @PostMapping(
            value = "/get_seller_list",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SellerEntity>> getSellersBasedOnFilters(
            @RequestBody final SellerEntity filters) {
        return new ResponseEntity<>(sellerService.getSellersBasedOnFilters(filters), HttpStatus.OK);
    }

    @PostMapping(
            value = {"/sellers", "/post_seller"},
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSeller(@RequestBody SellerEntity sellerEntity) {
        sellerService.createSeller(sellerEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
            value = "/get_plans",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Plan>> getSellerPlans(
            @RequestParam(value = "id") String id) {
        return new ResponseEntity<>(sellerService.getSellerPlans(id), HttpStatus.OK);
    }

    @GetMapping(
            value = "/sellers/{sellerId}/plans",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Plan>> getPlans(
            @PathVariable(name = "sellerId") final String sellerId) {
        return new ResponseEntity<>(sellerService.getSellerPlans(sellerId), HttpStatus.OK);
    }

    @PostMapping(value = "/sellers/{sellerId}/plans",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Plan> createPlan(
            @PathVariable(name = "sellerId") final String sellerId,
            @RequestBody final Plan plan) {
        sellerService.createSellerPlan(sellerId, plan);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
}
