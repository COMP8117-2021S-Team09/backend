package com.tiffin_umbrella.first_release_1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Contact_User {
    private String phone;
    private String email;
    private Address_User address;
}
