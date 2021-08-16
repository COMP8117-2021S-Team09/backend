package com.tiffin_umbrella.first_release_1.service;

import com.tiffin_umbrella.first_release_1.entity.*;
import com.tiffin_umbrella.first_release_1.repository.SellerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class SellerServiceTest {
    @Autowired
    private SellerService sellerService;
    @MockBean
    private SellerRepository sellerRepository;
    @Test
    void findAll(){
        Collection<SellerEntity> sellers = new ArrayList<SellerEntity>();
        Set<Categories> categories = new HashSet<Categories>();
        Set<Cuisines> cuisines = new HashSet<Cuisines>();
        List<PlanEntity>plans = new ArrayList<PlanEntity>();
        Set<PaymentMethods> methods = new HashSet<PaymentMethods>();
        List<ReviewEntity> reviews = new ArrayList<>();
        List<AuditEntity> audits = new ArrayList<>();
        categories.add(Categories.VEG);
        categories.add(Categories.NON_VEG);
        cuisines.add(Cuisines.CHINESE);
        cuisines.add(Cuisines.GUJARATI);
        cuisines.add(Cuisines.INTERNATIONAL);
        methods.add(PaymentMethods.ONLINE);
        methods.add(PaymentMethods.COD);
        ReviewEntity r1 = ReviewEntity.builder()
                .buyer_id("1")
                .seller_id("2")
                .plan_id("3")
                .comments("asjkhdsjhds")
                .build();
        ReviewEntity r2 = ReviewEntity.builder()
                .buyer_id("4")
                .seller_id("5")
                .plan_id("6")
                .comments("uerhfoefjdbw")
                .build();
        ContactEntity c1 = ContactEntity
                .builder()
                .email("rbc@gmail.com")
                .phone("6473821882")
                .address(AddressEntity.builder()
                        .city("abc")
                        .country("China")
                        .line1("qwee")
                        .line2("sfsaf")
                        .latitude(89.09)
                        .longitude(90.12)
                        .state("Wangho")
                        .zip("N45S98")
                        .build())
                .build();
        PlanEntity p1 = PlanEntity.builder()
                .id("1")
                .name("Plan 1")
                .description("This is Plan 1")
                .price(250.25)
                .type(PlanType.WEEKLY)
                .status(PlanStatus.AVAILABLE)
                .imageUrl("https://i.ibb.co/qjfWZxR/chinese-img6.jpg")
                .videoUrl("")
                .modelUrl("https://sketchfab.com/models/aeadafce557445df8d852109f2794f6a/embed")
                .build();
        PlanEntity p2 = PlanEntity.builder()
                .id("2")
                .name("Plan 2")
                .description("This is Plan 2")
                .price(350.25)
                .type(PlanType.MONTHLY)
                .status(PlanStatus.NOT_AVAILABLE)
                .imageUrl("https://i.ibb.co/VWGjgr3/tari-chicken-with-rice-resized.jpg")
                .videoUrl("")
                .modelUrl("https://sketchfab.com/models/6b4275c9aba64e109ee606628e07d01a/embed")
                .build();
        AuditEntity a1 = AuditEntity.builder()
                .seller_id("12345678")
                .admin_id("91011121314")
                .comments("oasijdoaisjdiajd")
                .hygiene_rating(HygieneRating.GOOD)
                .overall_rating(OverallRating.BAD)
                .taste_rating(TasteRating.EXCELLENT)
                .build();
        AuditEntity a2 = AuditEntity.builder()
                .seller_id("12345678")
                .admin_id("91011121314")
                .comments("oasijdoaisjdiajd")
                .hygiene_rating(HygieneRating.GOOD)
                .overall_rating(OverallRating.BAD)
                .taste_rating(TasteRating.EXCELLENT)
                .build();
        plans.add(p1);
        plans.add(p2);
        reviews.add(r1);
        reviews.add(r2);
        audits.add(a1);
        audits.add(a2);
        SellerEntity s1 = SellerEntity.builder()
                .name("Ms Tiffins")
                .averagePricePerPerson(20.78)
                .description("best tifin service")
                .status(Status.VERIFIED)
                .imageUrl("https://i.ibb.co/yRh6sSX/non-veg-thali-resized.jpg")
                .categories(categories)
                .cuisines(cuisines)
                .plans(plans)
                .paymentMethodsAvailable(methods)
                .contact(c1)
                .password("5633456")
                .otp("1234")
                .vaccine(VaccineEntity.builder()
                        .dose1Url("www.dose1.com")
                        .dose2Url("www.dose2.com")
                        .build())
                .reviews(reviews)
                .audits(audits)
                .build();
        SellerEntity s2 = SellerEntity.builder()
                .name("Yogi Tiffins")
                .averagePricePerPerson(10.78)
                .description("tifin service")
                .status(Status.NOT_VERIFIED)
                .imageUrl("https://i.ibb.co/yRh6sSX/non-veg-thali-resized.jpg")
                .categories(categories)
                .cuisines(cuisines)
                .plans(plans)
                .paymentMethodsAvailable(methods)
                .contact(c1)
                .password("2345")
                .otp("6789")
                .vaccine(VaccineEntity.builder()
                        .dose1Url("www.dose1.com")
                        .dose2Url("www.dose2.com")
                        .build())
                .reviews(reviews)
                .audits(audits)
                .build();
        sellers.add(s1);
        sellers.add(s2);
        doReturn(sellers).when(sellerRepository).findAll();
        Collection<SellerEntity>sellerList = sellerService.findAll();
        Assertions.assertEquals(2,sellerList.size(),"find all should return 2 sellers");
        Assertions.assertSame(sellerList.toArray()[0],s1,"First Seller returned true");
        Assertions.assertSame(sellerList.toArray()[1],s2,"Second Seller returned ture");
       }
    @Test
    void getOrdersForSeller() {
        
    }

    @Test
    void getSellersBasedOnFilters() {
    }

    @Test
    void createSeller() {
    }

    @Test
    void getSellerPlans() {
    }

    @Test
    void createSellerPlan() {
    }
}