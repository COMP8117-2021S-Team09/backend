package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.adapter.BuyerAdapter;
import com.tiffin_umbrella.first_release_1.common.BadRequestException;
import com.tiffin_umbrella.first_release_1.common.ErrorCode;
import com.tiffin_umbrella.first_release_1.dto.BuyerDto;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import com.tiffin_umbrella.first_release_1.entity.OrderEntity;
import com.tiffin_umbrella.first_release_1.entity.PlanEntity;
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

    public void createBuyer(final BuyerDto buyer) {
        final BuyerEntity existingBuyer = buyerRepository.findByContact_Email(buyer.getContact().getEmail())
                .orElse(BuyerAdapter.adaptForCreation(buyer));
        buyerRepository.save(existingBuyer);/* will save if not exists already */
        final SellerEntity existingSeller = sellerRepository.findById(buyer.getSeller_id())
                .orElseThrow(() -> new BadRequestException(ErrorCode.SELLER_NOT_FOUND_BY_ID, buyer.getSeller_id()));
        final PlanEntity existingPlan = planRepository.findById(buyer.getPlan_id())
                .orElseThrow(() -> new BadRequestException(ErrorCode.PLAN_NOT_FOUND_BY_ID, buyer.getPlan_id()));
        final OrderEntity order = OrderEntity.builder()
                .buyer(existingBuyer)
                .seller(existingSeller)
                .plan(existingPlan).build();
        orderRepository.save(order);
        final String summary = getSummary(existingBuyer, existingSeller, existingPlan, order);
        mailSenderService.sendRegisterEmail(existingBuyer.getContact().getEmail());
        mailSenderService.sendSummaryEmail(existingBuyer.getContact().getEmail(), summary);
    }

    private String getSummary(final BuyerEntity buyer,
                              final SellerEntity seller,
                              final PlanEntity plan,
                              final OrderEntity order) {
        return "Hello " + buyer.getFirstName() + " " + buyer.getLastName() + "\n" +
                "We have received your tiffin order \n" +
                "Order Details\n" +
                "Order ID: " + order.getId() + "\n" +
                "Seller Name: " + seller.getName() + "\n" +
                "Plan Name: " + plan.getName() + "\n" +
                "Plan Description: " + plan.getDescription() + "\n" +
                "Plan type: " + plan.getType() + "\n" +
                "Contact Seller at this number for further assistance : " +
                seller.getContact().getPhone() + "\n" +
                "Enjoy your meal" + "\n" +
                "Thanks," + "\n" +
                "Team Tiffin Umbrella";
    }
}
