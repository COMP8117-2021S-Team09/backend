package com.tiffin_umbrella.first_release_1.adapter;

import com.tiffin_umbrella.first_release_1.common.ErrorMessage;
import com.tiffin_umbrella.first_release_1.dto.*;
import com.tiffin_umbrella.first_release_1.entity.*;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SellerAdapter {

    public static SellerEntity adaptForCreation(final SellerDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return SellerEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Status.NOT_VERIFIED)
                .plans(Collections.emptyList())
                .password(dto.getPassword())
                .categories(dto.getCategories())
                .cuisines(dto.getCuisines())
                .averagePricePerPerson(dto.getAveragePricePerPerson())
                .contact(ContactAdapter.adaptForCreation(dto.getContact()))
                .paymentMethodsAvailable(dto.getPaymentMethodsAvailable())
                .imageUrl(dto.getImageUrl())
                .vaccine(adaptVaccine(dto.getVaccine()))
                .otp(dto.getOtp())
                .build();
    }

    public static SellerEntity adaptForSearch(final SellerDto dto) {
        Assert.notNull(dto, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        return SellerEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .categories(dto.getCategories())
                .cuisines(dto.getCuisines())
                .averagePricePerPerson(dto.getAveragePricePerPerson())
                .paymentMethodsAvailable(dto.getPaymentMethodsAvailable())
                .imageUrl(dto.getImageUrl())
                .vaccine(adaptVaccine(dto.getVaccine()))
                .build();
    }

    public static Collection<SellerDto> adaptCollection(Collection<SellerEntity> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(SellerAdapter::adaptToDto)
                .collect(Collectors.toList());
    }

    public static SellerDto adaptToDto(final SellerEntity entity) {
        Assert.notNull(entity, ErrorMessage.VALIDATION_INVALID_INPUT_EMPTY);
        final SellerDto seller = SellerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .cuisines(entity.getCuisines())
                .averagePricePerPerson(entity.getAveragePricePerPerson())
                .categories(entity.getCategories())
                .imageUrl(entity.getImageUrl())
                .paymentMethodsAvailable(entity.getPaymentMethodsAvailable())
                .plans(PlanAdapter.adaptCollection(entity.getPlans()))
                .vaccine(adaptVaccine(entity.getVaccine()))
                .audits(adaptAudits(entity.getAudits()))
                .reviews(adaptReviews(entity.getReviews()))
                .contact(ContactAdapter.adaptContact(entity.getContact()))
                .build();
        return addDynamicDetails(seller, entity);
    }

    private static SellerDto addDynamicDetails(final SellerDto dto, final SellerEntity entity) {
        dto.setAveragePricePerPerson(calculateAveragePricePerson(entity));
        dto.getCategories().addAll(calculateCategories(entity));
        dto.getCuisines().addAll(calculateCuisines(entity));
        return dto;
    }

    private static Set<Cuisines> calculateCuisines(final SellerEntity seller) {
        return seller.getPlans().stream()
                .map(PlanEntity::getCuisine).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static Set<Categories> calculateCategories(final SellerEntity seller) {
        return seller.getPlans().stream()
                .map(PlanEntity::getCategory).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static double calculateAveragePricePerson(final SellerEntity seller) {
        final int numberOfPlans = seller.getPlans().size();
        final double totalPlanPricePerDay = seller.getPlans().stream()
                .flatMapToDouble(planEntity -> DoubleStream.of(planEntity.getPlanPricePerDay()))
                .sum();
        return roundOff(totalPlanPricePerDay / numberOfPlans);
    }

    private static Collection<ReviewDto> adaptReviews(final Collection<ReviewEntity> reviews) {
        return Optional.ofNullable(reviews).orElse(Collections.emptyList()).stream()
                .map(review -> ReviewDto.builder()
                        .buyerId(review.getBuyer_id())
                        .comments(review.getComments())
                        .sellerId(review.getSeller_id())
                        .planId(review.getPlan_id())
                        .build())
                .collect(Collectors.toList());
    }

    private static Collection<AuditDto> adaptAudits(final Collection<AuditEntity> audits) {
        return Optional.ofNullable(audits).orElse(Collections.emptyList()).stream()
                .map(audit -> AuditDto.builder()
                        .adminId(audit.getAdmin_id())
                        .sellerId(audit.getSeller_id())
                        .comments(audit.getComments())
                        .tasteRating(audit.getTaste_rating())
                        .hygieneRating(audit.getHygiene_rating())
                        .overallRating(audit.getOverall_rating())
                        .build())
                .collect(Collectors.toList());
    }

    private static VaccineDto adaptVaccine(final VaccineEntity entity) {
        final VaccineEntity vaccine = Optional.ofNullable(entity).orElse(new VaccineEntity());
        return VaccineDto.builder()
                .dose1Date(vaccine.getDose1Date())
                .dose1Url(vaccine.getDose1Url())
                .dose2Date(vaccine.getDose2Date())
                .dose2Url(vaccine.getDose2Url())
                .build();
    }

    private static VaccineEntity adaptVaccine(final VaccineDto vaccine) {
        final VaccineDto dto = Optional.ofNullable(vaccine).orElse(new VaccineDto());
        return VaccineEntity.builder()
                .dose1Date(dto.getDose1Date())
                .dose1Url(dto.getDose1Url())
                .dose2Date(dto.getDose2Date())
                .dose2Url(dto.getDose2Url())
                .build();
    }

    private static Double roundOff(final Double value) {
        return Math.round(value * 100D) / 100D;
    }
}
