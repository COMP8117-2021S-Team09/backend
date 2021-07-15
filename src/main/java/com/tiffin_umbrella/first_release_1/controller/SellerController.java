package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import com.tiffin_umbrella.first_release_1.service.MailSenderService;
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
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MailSenderService mailSenderService;
    @GetMapping("/get_seller_list")
    public List<SellerEntity> get_sellers() {
        return sellerRepository.findAll();
    }

    @GetMapping("/get_orders")
	public Collection<Order> get_orders(@RequestParam (value = "seller_id") String id){
    	return orderRepository.findBySeller_Id(id);
    }
}
