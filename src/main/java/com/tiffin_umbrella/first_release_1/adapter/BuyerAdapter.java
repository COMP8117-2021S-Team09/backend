package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.BuyerDto;
import com.tiffin_umbrella.first_release_1.entity.BuyerEntity;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BuyerAdapter {

    public static BuyerDto adaptToDto(final BuyerEntity entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return BuyerDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .contact(ContactAdapter.adaptContact(entity.getContact()))
                .build();
    }

    public static BuyerEntity adaptForCreation(final BuyerDto buyer) {
        return BuyerEntity.builder()
                .firstName(buyer.getFirstName())
                .lastName(buyer.getLastName())
                .contact(ContactAdapter.adaptForCreation(buyer.getContact()))
                .build();
    }
}
