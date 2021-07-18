package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(value="plan")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlanEntity {
    @Id
    String id;
    String name;
    String description;
    Double price;
    @Field(value = "type", targetType = FieldType.STRING)
    PlanType type;
    @Field(value = "status", targetType = FieldType.STRING)
    PlanStatus status;
    String imageUrl;
    String videoUrl;
    String modelUrl;

}
