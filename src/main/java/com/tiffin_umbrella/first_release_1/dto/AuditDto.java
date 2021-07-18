package com.tiffin_umbrella.first_release_1.dto;

import com.tiffin_umbrella.first_release_1.entity.HygieneRating;
import com.tiffin_umbrella.first_release_1.entity.OverallRating;
import com.tiffin_umbrella.first_release_1.entity.TasteRating;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuditDto {

    private String sellerId;
    private String adminId;
    private String comments;

    private OverallRating overallRating;
    private HygieneRating hygieneRating;
    private TasteRating tasteRating;

    private Instant createdAt;
}
