package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Review {
    String seller_id;
    String buyer_id;
    String plan_id;
    String comments;
}
