package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.user.AuthUserDto;
import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.CreateUserDto;

import com.example.LaptopKG.exception.UserAlreadyExistException;
import com.example.LaptopKG.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody CreateUserDto request
    ) throws UserAlreadyExistException {
        return ResponseEntity.ok(service.register(request));
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthUserDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }




}
