package com.tiffin_umbrella.first_release_1.dto;

import com.tiffin_umbrella.first_release_1.entity.*;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
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
    private String password;
    private String otp;
    private VaccineDto vaccine;
    private Collection<ReviewDto> reviews;
    private Collection<AuditDto> audits;

    @Valid
    @NotNull(message = "seller contact cannot be empty")
    private ContactDto contact;

    public Set<Categories> getCategories() {
        return Optional.ofNullable(categories).orElse(Collections.emptySet());
    }

    public Set<Cuisines> getCuisines() {
        return Optional.ofNullable(cuisines).orElse(Collections.emptySet());
    }
}
