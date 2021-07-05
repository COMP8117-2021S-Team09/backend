package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Order {
    @DBRef
    SellerEntity seller;
    @DBRef
    BuyerEntity buyerEntity;
    @DBRef
    Plan plan_entity;
}
