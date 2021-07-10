package com.tiffin_umbrella.first_release_1.controller;

import com.tiffin_umbrella.first_release_1.entity.*;
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
    
    @GetMapping("/get_seller_list")
    public List<SellerEntity> get_sellers() {
        return sellerRepository.findAll();
    }


    @PostMapping("/post_seller")
    public String post_seller(@RequestBody SellerEntity seller) {
        final Contact contact = seller.getContact();
    	final String email = contact.getEmail();
    	final String password = seller.getPassword();
    	
    	SellerEntity s =  sellerRepository.findByContact_Email(email);
    	final String actualPassword = s.getPassword();
    	
    	if (s != null){
            if(password.equals(actualPassword)){
                return "matched";
            }
            else{
            return "not matched";
            }
        }
        else{
            return "user not found!";
        }
    }
}
