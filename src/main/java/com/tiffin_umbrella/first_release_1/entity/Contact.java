package com.tiffin_umbrella.first_release_1.entity;

import com.tiffin_umbrella.first_release_1.entity.Address;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Contact {
    private String phone;
    private String email;
    private Address address;
}
