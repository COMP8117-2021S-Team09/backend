package com.tiffin_umbrella.first_release_1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address_User{
    String line1;
    String area;
    String zip;
}
