package com.tiffin_umbrella.first_release_1.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.util.List;
import java.util.Set;
import lombok.*;
@Document(value="seller")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SellerEntity {
    @Id
    private String id;
    @Field(value="name",targetType= FieldType.STRING)
    private String name;
    @Field(value="description",targetType= FieldType.STRING)
    String description;
    @Field(value="averagePricePerPerson",targetType = FieldType.DOUBLE)
    Double averagePricePerPerson;
    @Field(value="status",targetType=FieldType.STRING)
    private Status status;
    @Field(value="imageUrl",targetType=FieldType.STRING)
    private String imageUrl;
    @Field(value="categories")
    private Set<Categories> categories;
    @Field(value="cuisines")
    private Set<Cuisines> cuisines;
    @DBRef
    private List<Plan>plans;
    @Field(value="paymentsMethods")
    private Set<PaymentMethods>paymentMethodsAvailable;
    @Field(value="contact")
    private Contact contact;
    @Field(value="password",targetType=FieldType.STRING)
    private String password;
    @Field(value="otp",targetType=FieldType.STRING)
    private String otp;
    @Field(value="vaccine")
    private Vaccine vaccine;
    @Field(value="reviews")
    private List<Review>reviews;
    @Field(value="audits")
    private List<Audit>audits;
}
