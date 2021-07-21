package com.tiffin_umbrella.first_release_1.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyerDto {

    private String id;
    private String firstName;
    private String lastName;

    @NotEmpty(message = "order plan_id cannot be empty")
    private String plan_id;

    @NotEmpty(message = "order seller_id cannot be empty")
    private String seller_id;

    @Valid
    @NotNull(message = "buyer contact cannot be empty")
    private ContactDto contact;
}
