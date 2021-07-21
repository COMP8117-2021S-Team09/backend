package com.tiffin_umbrella.first_release_1.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="buyer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyerEntity{
     @Id
     private String id;
     String firstName;
     String lastName;
     ContactEntity contact;
}
