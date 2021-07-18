package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressDto {

    private Double latitude;
    private Double longitude;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;
}
