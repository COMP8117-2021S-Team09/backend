package com.tiffin_umbrella.first_release_1.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Vaccine {
    String dose1Url;
    Date dose1Date;
    String dose2Url;
    Date dose2Date;
}

