package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyerDto {

    private String id;
    private String firstName;
    private String lastName;

    private String plan_id;
    private String seller_id;

    @Valid
    @NotNull(message = "buyer contact cannot be empty")
    private ContactDto contact;
}
