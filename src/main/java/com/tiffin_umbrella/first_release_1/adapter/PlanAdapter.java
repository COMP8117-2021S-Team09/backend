package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.PlanDto;
import com.tiffin_umbrella.first_release_1.dto.SellerDto;
import com.tiffin_umbrella.first_release_1.entity.*;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PlanAdapter {

    public static Plan adaptForCreation(final PlanDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return Plan.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Optional.ofNullable(dto.getStatus()).orElse(PlanStatus.AVAILABLE))
                .type(Optional.ofNullable(dto.getType()).orElse(PlanType.ONCE))
                .build();
    }

    public static Collection<PlanDto> adaptCollection(Collection<Plan> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(PlanAdapter::adaptToDto)
                .collect(Collectors.toList());
    }

    public static PlanDto adaptToDto(final Plan entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return PlanDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .videoUrl(entity.getVideoUrl())
                .modelUrl(entity.getModelUrl())
                .build();
    }
}
