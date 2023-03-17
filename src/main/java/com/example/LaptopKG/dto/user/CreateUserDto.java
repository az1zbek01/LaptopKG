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

    @NotNull(message = "Username should not be null")
    @NotBlank(message = "Username should not be blank")
    @Size(min = 8, max = 30, message = "Must be of 8 - 30 characters")
    String username;

    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be blank")
    @Size(min = 1, max = 30, message = "Must be of 1 - 30 characters")
    String password;

    @NotNull(message = "Email should not be null")
    @NotBlank(message = "Email should not be blank")
    @Email
    String email;

    String firstName;

    String lastName;

    String phoneNumber;

    String address;


}
