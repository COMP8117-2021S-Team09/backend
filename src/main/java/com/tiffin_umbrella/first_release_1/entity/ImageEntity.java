package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(value = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageEntity {
    @Id
    private String id;

    private String url;
    private String comment;

    private Binary imageBinary;
    private Long sizeInKb;

    private Instant createdAt;
}
