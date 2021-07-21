package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContactDto {

    @NotEmpty(message = "contact info cannot exist without email")
    @Email
    private String email;
    private String phone;
    private AddressDto address;
}
