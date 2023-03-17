package com.example.LaptopKG.dto.user;

import com.example.LaptopKG.model.Image;
import com.example.LaptopKG.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserDto {


    String username;
    String email;
    String address;
    String phoneNumber;
    String firstName;
    String lastName;
    Image avatar;

    public GetUserDto getUserDto(User user){
        return GetUserDto.builder()
                .username(user.getRealUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .build();
    }

}
