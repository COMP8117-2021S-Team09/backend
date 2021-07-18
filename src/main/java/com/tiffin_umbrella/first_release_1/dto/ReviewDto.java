package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDto {

    private String seller_id;
    private String buyer_id;
    private String plan_id;
    private String comments;
}
