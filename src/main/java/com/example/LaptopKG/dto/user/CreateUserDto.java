package com.example.LaptopKG.dto.user;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserDto {

    @Size(min = 8, max = 30, message = "Must be of 8 - 30 characters")
    String username;

    @Size(min = 1, max = 30, message = "Must be of 1 - 30 characters")
    String password;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid email address")
    String email;

    String firstName;

    String lastName;

    String phoneNumber;

    String address;


}
