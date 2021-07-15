package com.tiffin_umbrella.first_release_1.controller;
import com.tiffin_umbrella.first_release_1.Presentation_Layer.Buyer;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.entity.Order;
import com.tiffin_umbrella.first_release_1.entity.Plan;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import com.tiffin_umbrella.first_release_1.repository.BuyerRepository;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import com.tiffin_umbrella.first_release_1.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class BuyerController {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    BuyerRepository buyerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MailSenderService mailSenderService;
    @PostMapping("/post_buyer")
    public void post_buyer(@RequestBody final Buyer buyer){
        BuyerEntity buyerEntity = new BuyerEntity();
        buyerEntity.setFirstName(buyer.getFirstName());
        buyerEntity.setLastName(buyer.getLastName());
        buyerEntity.setContact(buyer.getContact());
        BuyerEntity saved_buyer = buyerRepository.save(buyerEntity);
        SellerEntity saved_seller = sellerRepository.findById(buyer.getSeller_id()).get();
        Plan saved_plan = planRepository.findById(buyer.getPlan()).get();
        Order order =new Order();
        order.setBuyer(saved_buyer);
        order.setSeller(saved_seller);
        order.setPlan(saved_plan);
        Order saved_order = orderRepository.save(order);
        //saved_buyer.setOrder_id(saved_order);
        buyerRepository.save(saved_buyer);
        mailSenderService.send_Register_Email(buyerEntity.getContact().getEmail());
        mailSenderService.send_Summary_Email(buyerEntity.getContact().getEmail(),this.getSummary(saved_buyer,saved_seller,saved_plan));
    }
  public String getSummary(BuyerEntity buyer, SellerEntity seller, Plan plan) {
      String summary = "Hello " + buyer.getFirstName() + " " + buyer.getLastName() + "\n" +
              "We have recieved your tiffin order \n" +
              "Order Details\n" +
              "Seller Name :"+seller.getName()+"\n"+
              "Plan Name: "+plan.getName()+"\n"+
              "Plan Description"+plan.getDescription()+"\n"+
              "Plan Type: "+plan.getType()+"\n"+
              "Contact Seller at this number for further assistance : "+seller.getContact().getPhone()+"\n"+
              "Enjoy your meal"+"\n"+
              "Thanks,"+"\n"+
              "Team Tiffin Umbrella";
      return summary;
  }
}
