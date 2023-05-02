package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.user.AuthUserDto;
import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.CreateUserDto;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.exception.TokenNotValidException;
import com.example.LaptopKG.exception.UserAlreadyExistException;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Role;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailService;

    public ResponseEntity<String> register(CreateUserDto request) throws UserAlreadyExistException {
        if(repository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistException(
                    "email",
                    "User with this email is already exists"
            );
        if(repository.existsByUsername(request.getUsername()))
            throw new UserAlreadyExistException(
                    "username",
                    "User with this username is already exists"
            );
        Random random = new Random();
        var user = User.builder()
                .status(Status.NOT_ACTIVATED)
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .token(String.valueOf(random.nextInt(1000, 9999)))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        // Email message
        SimpleMailMessage activationEmail = new SimpleMailMessage();
        activationEmail.setFrom("laptopkginai@gmail.com");
        activationEmail.setTo(user.getEmail());
        activationEmail.setSubject("Account activation");
        activationEmail.setText("To activate your account enter this code: " + user.getToken());

        emailService.sendEmail(activationEmail);
        log.info("Success sent email to " + user.getEmail());

        return ResponseEntity.ok("Successfully registered! Your activate code was sent to email.");
    }

    public AuthenticationResponse authenticate(AuthUserDto request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new NotFoundException("Email wasn't found")
        );
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) throws IOException {
        final String userEmail;

        userEmail = jwtService.extractUsername(refreshToken); // extract the user Email from token;

        var user = repository.findByEmail(userEmail).orElseThrow(
                () -> new NotFoundException("User with email " + userEmail + " wasn't found")
        );

        if(jwtService.isTokenValid(refreshToken, user)){
            return AuthenticationResponse.builder()
                    .accessToken(jwtService.generateToken(user))
                    .refreshToken(jwtService.generateRefreshToken(user))
                    .build();
        }

        throw new TokenNotValidException("Token is not valid");
    }

    public ResponseEntity<String> activateAccount(String token) {

        // Find the user associated with the activation token
        Optional<User> user = repository.findByToken(token);

        // Check user exists by token
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token!");
        }

        User activateUser = user.get();

        // Set account enabled
        activateUser.setStatus(Status.ACTIVE);

        // Set the activation token to null so it cannot be used again
        activateUser.setToken(null);
        repository.save(activateUser);
        return ResponseEntity.ok().body("Account activated successfully");

    }
}
