package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.OrderDto;
import com.tiffin_umbrella.first_release_1.dto.PlanDto;
import com.tiffin_umbrella.first_release_1.entity.Order;
import com.tiffin_umbrella.first_release_1.entity.Plan;
import com.tiffin_umbrella.first_release_1.entity.PlanStatus;
import com.tiffin_umbrella.first_release_1.entity.PlanType;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderAdapter {

    public static Order adaptForCreation(final OrderDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return Order.builder()
                .build();
    }

    public static Collection<OrderDto> adaptCollection(Collection<Order> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(OrderAdapter::adaptToDto)
                .collect(Collectors.toList());
    }

    public static OrderDto adaptToDto(final Order entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return OrderDto.builder()
                .id(entity.getId())
                .plan(PlanAdapter.adaptToDto(entity.getPlan()))
                .seller(SellerAdapter.adaptToDto(entity.getSeller()))
                .buyer(BuyerAdapter.adaptToDto(entity.getBuyer()))
                .build();
    }
}
