package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value="order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Order {
    @Id
    private String id;
    @DBRef
    SellerEntity seller;
    @DBRef
    BuyerEntity buyer;
    @DBRef
    Plan plan;
}
