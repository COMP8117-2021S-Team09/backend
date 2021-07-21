package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.dto.AddressDto;
import com.tiffin_umbrella.first_release_1.dto.ContactDto;
import com.tiffin_umbrella.first_release_1.entity.*;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ContactAdapter {

    public static ContactEntity adaptForCreation(final ContactDto dto) {
        return ContactEntity.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(adaptAddress(dto.getAddress()))
                .build();
    }

    public static ContactDto adaptContact(final ContactEntity toBeAdapted) {
        final ContactEntity contact = Optional.ofNullable(toBeAdapted)
                .orElse(ContactEntity.builder().build());
        return ContactDto.builder()
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .address(adaptAddress(contact.getAddress()))
                .build();
    }

    private static AddressDto adaptAddress(final AddressEntity toBeAdapted) {
        final AddressEntity address = Optional.ofNullable(toBeAdapted)
                .orElse(AddressEntity.builder().build());
        return AddressDto.builder()
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .line1(address.getLine1())
                .line2(address.getLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zip(address.getZip())
                .build();
    }

    private static AddressEntity adaptAddress(final AddressDto address) {
        final AddressDto dto = Optional.ofNullable(address).orElse(new AddressDto());
        return AddressEntity.builder()
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .line1(dto.getLine1())
                .line2(dto.getLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zip(dto.getZip())
                .build();
    }
}
