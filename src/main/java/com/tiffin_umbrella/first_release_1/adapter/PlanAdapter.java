package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.PlanDto;
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

    public static PlanEntity adaptForCreation(final PlanDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return PlanEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Optional.ofNullable(dto.getStatus()).orElse(PlanStatus.AVAILABLE))
                .type(Optional.ofNullable(dto.getType()).orElse(PlanType.ONCE))
                .cuisine(Optional.ofNullable(dto.getCuisine()).orElse(Cuisines.INTERNATIONAL))
                .category(Optional.ofNullable(dto.getCategory()).orElse(Categories.VEG))
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .modelUrl(dto.getModelUrl())
                .build();
    }

    public static Collection<PlanDto> adaptCollection(Collection<PlanEntity> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(PlanAdapter::adaptToDto)
                .collect(Collectors.toList());
    }

    public static PlanDto adaptToDto(final PlanEntity entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return PlanDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .cuisine(entity.getCuisine())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .videoUrl(entity.getVideoUrl())
                .modelUrl(entity.getModelUrl())
                .build();
    }
}
