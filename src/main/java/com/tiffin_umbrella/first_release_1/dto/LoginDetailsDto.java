package com.tiffin_umbrella.first_release_1.dto;

import com.tiffin_umbrella.first_release_1.entity.Role;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginDetailsDto {

    private String id;

    @NotEmpty(message = "email missing")
    private String email;

    @NotEmpty(message = "password missing")
    private String password;

    @NotNull(message = "role missing with allowed values: [SELLER, USER, ADMIN]")
    private Role role;

    private String token;
    private String message;
}
