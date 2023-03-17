package com.example.LaptopKG.dto.user;


import com.example.LaptopKG.model.Image;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDto {

    String username;
    String email;
    String address;
    String phoneNumber;
    String firstName;
    String lastName;
    Image avatar;

}
