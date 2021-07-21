package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document("audit")
public class AuditEntity {
    String seller_id;
    String admin_id;
    String comments;
    @Field(value = "overall_rating", targetType = FieldType.STRING)
    OverallRating overall_rating;
    @Field(value = "hygiene_rating", targetType = FieldType.STRING)
    HygieneRating hygiene_rating;
    @Field(value = "taste_rating", targetType = FieldType.STRING)
    TasteRating taste_rating;

}
