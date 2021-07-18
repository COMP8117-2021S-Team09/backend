package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDto {

    private String sellerId;
    private String buyerId;
    private String planId;
    private String comments;
}
