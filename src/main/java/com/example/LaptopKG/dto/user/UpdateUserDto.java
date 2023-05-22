package com.example.LaptopKG.dto.user;


import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDto {
    String username;
    @Email(message = "Почта некорректна")
    String email;
    String address;
    String phoneNumber;
    String firstName;
    String lastName;

}
