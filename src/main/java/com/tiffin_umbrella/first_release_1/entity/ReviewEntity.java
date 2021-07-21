package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document("review")
public class ReviewEntity {
    String seller_id;
    String buyer_id;
    String plan_id;
    String comments;
}
