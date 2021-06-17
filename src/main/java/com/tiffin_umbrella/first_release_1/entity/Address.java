package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class Address {
    Double latitude;
    Double longitude;
    String line1;
    String line2;
    String city;
    String state;
    String zip;
    String country;

}
