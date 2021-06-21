package com.tiffin_umbrella.first_release_1.controller;
import com.tiffin_umbrella.first_release_1.entity.Plan;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin
@RestController
public class SellerController {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    PlanRepository planRepository;
    @GetMapping("/get_seller_list")
    public List<SellerEntity> get_sellers(){
        return sellerRepository.findAll();
    }
    @PostMapping("/post_seller")
    public void post_seller(@RequestBody SellerEntity sellerEntity){
       planRepository.saveAll(sellerEntity.getPlans());
       sellerRepository.save(sellerEntity);
    }
    @GetMapping("/get_plans")
    public List<Plan>get_plans(@RequestParam (value="id")String id){
        SellerEntity seller = sellerRepository.findById(id).get();
        List<Plan>plans = seller.getPlans();
        return plans;
    }
}
