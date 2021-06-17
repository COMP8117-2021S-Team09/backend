package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Audit {
    String seller_id;
    String admin_id;
    String comments;
    @Field(value="overall_rating",targetType = FieldType.STRING)
    OverallRating overall_rating;
    @Field(value="hygiene_rating",targetType = FieldType.STRING)
    HygieneRating hygiene_rating;
    @Field(value="taste_rating",targetType = FieldType.STRING)
    TasteRating taste_rating;

}
