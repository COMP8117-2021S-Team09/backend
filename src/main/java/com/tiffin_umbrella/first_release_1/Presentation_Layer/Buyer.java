package com.tiffin_umbrella.first_release_1.Presentation_Layer;
import com.tiffin_umbrella.first_release_1.entity.Contact;
import com.tiffin_umbrella.first_release_1.entity.Contact_User;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Buyer {
    String firstName;
    String lastName;
    Contact_User contact;
    String seller_id;
    String plan;
}
