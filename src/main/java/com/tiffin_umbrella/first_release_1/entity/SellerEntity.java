package com.tiffin_umbrella.first_release_1.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.*;

@Document(value = "seller")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SellerEntity {
    @Field(value = "description", targetType = FieldType.STRING)
    String description;
    @Field(value = "averagePricePerPerson", targetType = FieldType.DOUBLE)
    Double averagePricePerPerson;
    @Id
    private String id;
    @Field(value = "name", targetType = FieldType.STRING)
    private String name;
    @Field(value = "status", targetType = FieldType.STRING)
    private Status status;
    @Field(value = "imageUrl", targetType = FieldType.STRING)
    private String imageUrl;
    @Field(value = "categories")
    private Set<Categories> categories;
    @Field(value = "cuisines")
    private Set<Cuisines> cuisines;
    @DBRef
    private List<PlanEntity> plans;
    @Field(value = "paymentsMethods")
    private Set<PaymentMethods> paymentMethodsAvailable;
    @Field(value = "contact")
    private ContactEntity contact;
    @Field(value = "password", targetType = FieldType.STRING)
    private String password;
    @Field(value = "otp", targetType = FieldType.STRING)
    private String otp;
    @Field(value = "vaccine")
    private VaccineEntity vaccine;
    @Field(value = "reviews")
    private List<ReviewEntity> reviews;
    @Field(value = "audits")
    private List<AuditEntity> audits;

    public String getContactEmail() {
        return contact == null ? "" : contact.getEmail();
    }

    public Set<Categories> getCategories() {
        return Optional.ofNullable(categories).orElse(Collections.emptySet());
    }

    public Set<Cuisines> getCuisines() {
        return Optional.ofNullable(cuisines).orElse(Collections.emptySet());
    }
}
