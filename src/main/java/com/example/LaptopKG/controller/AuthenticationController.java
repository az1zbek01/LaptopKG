package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.user.AuthUserDto;
import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.CreateUserDto;

import com.example.LaptopKG.exception.UserAlreadyExistException;
import com.example.LaptopKG.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для авторизации, регистрации, подтверждения аккаунта"
)
public class AuthenticationController{

    private final UserService service;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового аккаунта"
    )
    public ResponseEntity<String> register(
            @Valid @RequestBody CreateUserDto request
    ) throws UserAlreadyExistException {
        return service.register(request);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация активированного аккаунта"
    )
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthUserDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление токена"
    )
    public ResponseEntity<AuthenticationResponse> refresh(@RequestParam String refreshToken) throws IOException {
        return ResponseEntity.ok(service.refreshToken(refreshToken));
    }

    @GetMapping("/activate/{token}")
    @Operation(
            summary = "Активация аккаунта с помощью кода, отправленного на почту"
    )
    public ResponseEntity<String> activate(@PathVariable String token) {
        return service.activateAccount(token);
    }
}
