package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.*;
import com.example.LaptopKG.exception.UserAlreadyExistException;
import com.example.LaptopKG.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> register(CreateUserDto request) throws UserAlreadyExistException;
    AuthenticationResponse authenticate(AuthUserDto request);
    AuthenticationResponse refreshToken(String refreshToken);
    ResponseEntity<String> activateAccount(String token);
    GetUserDto changeUserInfo(UpdateUserDto userDto, User user);
    ResponseEntity<String> addAdmin(CreateUserDto userDto);
}
