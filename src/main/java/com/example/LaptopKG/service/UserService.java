package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.AuthUserDto;
import com.example.LaptopKG.dto.user.CreateUserDto;
import com.example.LaptopKG.exception.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> register(CreateUserDto request) throws UserAlreadyExistException;
    AuthenticationResponse authenticate(AuthUserDto request);
    AuthenticationResponse refreshToken(String refreshToken);
    ResponseEntity<String> activateAccount(String token);

}
