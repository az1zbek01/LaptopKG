package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.user.AuthUserDto;
import com.example.LaptopKG.dto.AuthenticationResponse;
import com.example.LaptopKG.dto.user.CreateUserDto;
import com.example.LaptopKG.exception.TokenNotValidException;
import com.example.LaptopKG.exception.UserAlreadyExistException;
import com.example.LaptopKG.model.RefreshToken;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Role;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.RefreshTokenRepository;
import com.example.LaptopKG.repository.UserRepository;
import com.example.LaptopKG.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailService;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<String> register(CreateUserDto request) throws UserAlreadyExistException {
        if(repository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistException(
                    "email",
                    "Пользователь с такой почтой уже существует"
            );
        if(repository.existsByUsername(request.getUsername()))
            throw new UserAlreadyExistException(
                    "username",
                    "User with this username is already exists"
            );
        Random random = new Random();
        String token = String.valueOf(random.nextInt(100000, 999999));
        while(repository.existsByToken(token)){
            token = String.valueOf(random.nextInt(100000, 999999));
        }

        var user = User.builder()
                .status(Status.NOT_ACTIVATED)
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .token(token)
                .build();
        repository.save(user);

        SimpleMailMessage activationEmail = new SimpleMailMessage();
        activationEmail.setFrom("laptopKG@gmail.com");
        activationEmail.setTo(user.getEmail());
        activationEmail.setSubject("Активация аккаунта");
        activationEmail.setText("Для активации аккаунта введите следующий код: " + user.getToken());

        emailService.sendEmail(activationEmail);
        log.info("Код успешно отправлен на почту " + user.getEmail());

        return ResponseEntity.ok("Успешная регистрация! Ваш код активации был отправлен на почту.");
    }

    public AuthenticationResponse authenticate(AuthUserDto request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        if(refreshTokenRepository.existsByUserId(user.getId())){
            RefreshToken refToken = refreshTokenRepository.findByUserId(user.getId());
            refToken.setToken(refreshToken);
            refreshTokenRepository.save(refToken);
        }else{
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .token(refreshToken)
                            .userId(user.getId())
                            .build()
            );
        }

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        if(!refreshTokenRepository.existsByToken(refreshToken)){
            throw new TokenNotValidException("Токен не валидный");
        }

        final String userEmail;
        userEmail = jwtService.extractUsername(refreshToken);
        var user = repository.findByEmail(userEmail).orElseThrow();

        if(!jwtService.isTokenValid(refreshToken, user)){
            throw new TokenNotValidException("Токен не валидный");
        }

        RefreshToken refToken = refreshTokenRepository.findByToken(refreshToken);
        var newRefreshToken = jwtService.generateRefreshToken(user);
        refToken.setToken(newRefreshToken);
        refreshTokenRepository.save(refToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(newRefreshToken)
                .build();
    }

    public ResponseEntity<String> activateAccount(String token) {
        Optional<User> user = repository.findByToken(token);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный код");
        }

        User activateUser = user.get();
        activateUser.setStatus(Status.ACTIVE);

        activateUser.setToken(null);
        repository.save(activateUser);
        return ResponseEntity.ok().body("Аккаунт успешно активирован!");

    }
}
