package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VaccineDto {

    private String dose1Url;
    private Instant dose1Date;

    private String dose2Url;
    private Instant dose2Date;
}

