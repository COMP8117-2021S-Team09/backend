package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document("contact")
public class ContactEntity {
    private String phone;
    private String email;
    private AddressEntity address;
}
