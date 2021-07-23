package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.ImageDto;
import com.tiffin_umbrella.first_release_1.entity.ImageEntity;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageAdapter {

    public static ImageDto adapt(final ImageEntity entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return ImageDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .comment(entity.getComment())
                .sizeInKb(entity.getSizeInKb())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static Collection<ImageDto> adapt(final Collection<ImageEntity> imageEntities) {
        return Optional.ofNullable(imageEntities).orElse(Collections.emptyList()).stream()
                .map(ImageAdapter::adapt).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static ImageEntity create(final Binary binary, final String comment) {
        return ImageEntity.builder()
                .imageBinary(binary)
                .comment(comment)
                .createdAt(Instant.now())
                .build();
    }
}
