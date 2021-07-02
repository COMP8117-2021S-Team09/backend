package com.tiffin_umbrella.first_release_1.controller;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class BuyerController {
    @Autowired
    BuyerRepository buyerRepository;
    @PostMapping("/post_buyer")
    public void post_buyer(@RequestBody BuyerEntity buyerEntity){
        buyerRepository.save(buyerEntity);
    }
}
