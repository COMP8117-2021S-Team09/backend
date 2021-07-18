package com.tiffin_umbrella.first_release_1.dto;

import com.tiffin_umbrella.first_release_1.entity.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SellerDto {

    private String id;

    @NotEmpty(message = "seller name cannot be empty")
    private String name;
    private Status status;

    private String imageUrl;
    private String description;
    private Double averagePricePerPerson;

    private Set<Categories> categories;
    private Set<Cuisines> cuisines;
    private Set<PaymentMethods> paymentMethodsAvailable;

    private Collection<PlanDto> plans;
    private ContactDto contact;
    private String password;
    private String otp;
    private VaccineDto vaccine;
    private Collection<ReviewDto> reviews;
    private Collection<AuditDto> audits;
}
