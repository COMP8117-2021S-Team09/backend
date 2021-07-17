package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.common.BadRequestException;
import com.tiffin_umbrella.first_release_1.common.ErrorCode;
import com.tiffin_umbrella.first_release_1.dto.Buyer;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.entity.Order;
import com.tiffin_umbrella.first_release_1.entity.Plan;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import com.tiffin_umbrella.first_release_1.repository.BuyerRepository;
import com.tiffin_umbrella.first_release_1.repository.OrderRepository;
import com.tiffin_umbrella.first_release_1.repository.PlanRepository;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class BuyerService {

    private final SellerRepository sellerRepository;
    private final PlanRepository planRepository;
    private final BuyerRepository buyerRepository;
    private final OrderRepository orderRepository;
    private final MailSenderService mailSenderService;

    public void createBuyer(final Buyer buyer) {
        final BuyerEntity existingBuyer = buyerRepository.findByContact_Email(buyer.getContact().getEmail())
                .orElse(BuyerEntity.builder()
                        .firstName(buyer.getFirstName())
                        .lastName(buyer.getLastName())
                        .contact(buyer.getContact()).build());
        buyerRepository.save(existingBuyer);/* will save if not exists already */
        final SellerEntity existingSeller = sellerRepository.findById(buyer.getSeller_id())
                .orElseThrow(() -> new BadRequestException(ErrorCode.SELLER_NOT_FOUND_BY_ID, buyer.getSeller_id()));
        final Plan existingPlan = planRepository.findById(buyer.getPlan_id())
                .orElseThrow(() -> new BadRequestException(ErrorCode.PLAN_NOT_FOUND_BY_ID, buyer.getPlan_id()));
        final Order order = Order.builder()
                .buyer(existingBuyer)
                .seller(existingSeller)
                .plan(existingPlan).build();
        orderRepository.save(order);
        final String summary = getSummary(existingBuyer, existingSeller, existingPlan);
        mailSenderService.sendRegisterEmail(existingBuyer.getContact().getEmail());
        mailSenderService.sendSummaryEmail(existingBuyer.getContact().getEmail(), summary);
    }

    private String getSummary(final BuyerEntity buyer, final SellerEntity seller, final Plan plan) {
        return "Hello " + buyer.getFirstName() + " " + buyer.getLastName() + "\n" +
                "We have recieved your tiffin order \n" +
                "Order Details\n" +
                "Seller Name :" + seller.getName() + "\n" +
                "Plan Name: " + plan.getName() + "\n" +
                "Plan Description" + plan.getDescription() + "\n" +
                "Plan Type: " + plan.getType() + "\n" +
                "Contact Seller at this number for further assistance : " +
                seller.getContact().getPhone() + "\n" +
                "Enjoy your meal" + "\n" +
                "Thanks," + "\n" +
                "Team Tiffin Umbrella";
    }
}
