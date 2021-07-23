package com.tiffin_umbrella.first_release_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageDto {

    private String id;
    private String url;
    private String comment;
    private Long sizeInKb;

    private Instant createdAt;
}
