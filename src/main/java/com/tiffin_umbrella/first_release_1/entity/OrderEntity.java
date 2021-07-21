package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class OrderEntity {
    @Id
    private String id;
    @DBRef
    SellerEntity seller;
    @DBRef
    BuyerEntity buyer;
    @DBRef
    PlanEntity plan;
}
