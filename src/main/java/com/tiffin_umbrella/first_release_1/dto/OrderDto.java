package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDto {

    private String id;

    private SellerDto seller;
    private BuyerDto buyer;
    private PlanDto plan;
}
