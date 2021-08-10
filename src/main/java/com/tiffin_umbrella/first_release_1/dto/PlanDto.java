package com.tiffin_umbrella.first_release_1.dto;

import com.tiffin_umbrella.first_release_1.entity.Categories;
import com.tiffin_umbrella.first_release_1.entity.Cuisines;
import com.tiffin_umbrella.first_release_1.entity.PlanStatus;
import com.tiffin_umbrella.first_release_1.entity.PlanType;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlanDto {

    private String id;

    @NotEmpty(message = "plan name cannot be empty")
    private String name;
    private String description;
    private Double price;
    private PlanType type;
    private PlanStatus status;
    private Cuisines cuisine;
    private Categories category;

    @NotEmpty(message = "plan imageUrl cannot be empty")
    private String imageUrl;
    private String videoUrl;

    @NotEmpty(message = "plan modelUrl cannot be empty")
    private String modelUrl;
}
