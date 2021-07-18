package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Document("address")
public class AddressEntity {
    Double latitude;
    Double longitude;
    String line1;
    String line2;
    String city;
    String state;
    String zip;
    String country;

}
