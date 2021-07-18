package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.AddressDto;
import com.tiffin_umbrella.first_release_1.dto.ContactDto;
import com.tiffin_umbrella.first_release_1.dto.SellerDto;
import com.tiffin_umbrella.first_release_1.entity.Address;
import com.tiffin_umbrella.first_release_1.entity.Contact;
import com.tiffin_umbrella.first_release_1.entity.SellerEntity;
import com.tiffin_umbrella.first_release_1.entity.Status;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SellerAdapter {

    public static SellerEntity adaptForCreation(final SellerDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return SellerEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Status.NOT_VERIFIED)
                .plans(Collections.emptyList())
                .password(dto.getPassword())
                .build();
    }

    public static Collection<SellerDto> adaptCollection(Collection<SellerEntity> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(SellerAdapter::adaptToDto)
                .collect(Collectors.toList());
    }

    public static SellerDto adaptToDto(final SellerEntity entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return SellerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contact(ContactAdapter.adaptContact(entity.getContact()))
                .build();
    }
}
