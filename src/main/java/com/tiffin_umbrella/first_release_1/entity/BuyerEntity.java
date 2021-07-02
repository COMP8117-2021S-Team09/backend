package com.tiffin_umbrella.first_release_1.entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="buyer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyerEntity{
     String firstName;
     String lastName;
     Contact_User contact;
}
