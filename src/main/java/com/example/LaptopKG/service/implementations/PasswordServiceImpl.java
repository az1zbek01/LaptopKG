package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.user.ChangePasswordDTO;
import com.example.LaptopKG.dto.user.ResetPasswordDTO;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.exception.TokenNotValidException;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.repository.UserRepository;
import com.example.LaptopKG.service.PasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> forgotPassword(String userEmail) {
        // Get user by email or throw exception
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(
                        () -> new NotFoundException("Пользователь с почтой " + userEmail + " не найден")
                );

        // Generate random token for password resetting, set new token to user and save user
        Random random = new Random();
        String token = String.valueOf(random.nextInt(1000, 9999));
        while(userRepository.existsByToken(token)){
            token = String.valueOf(random.nextInt(1000, 9999));
        }

        user.setToken(token);
        userRepository.save(user);

        // Email message
        SimpleMailMessage activationEmail = new SimpleMailMessage();
        activationEmail.setFrom("laptopKG@gmail.com");
        activationEmail.setTo(user.getEmail());
        activationEmail.setSubject("Сброс пароля");
        activationEmail.setText("Для создания нового пароля введите следующий код: " + user.getToken());

        emailService.sendEmail(activationEmail);
        log.info("Код успешно отправлен на почту " + user.getEmail());

        return ResponseEntity.ok("Ваш код сброса пароля был отправлен на почту.");
    }

    @Override
    public ResponseEntity<String> setNewPassword(String token, ResetPasswordDTO password) {
        // Check if new password and password confirmation equals
        if(!password.getPassword().equals(password.getConfirmPassword())){
            return ResponseEntity.badRequest().body("Пароли не совпадают!");
        }

        // Get user by token or throw exception
        User user = userRepository.findByToken(token)
                .orElseThrow(
                        () -> new TokenNotValidException("Неверный токен.")
                );

        // Set new password, mark token as null and save user
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        user.setToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Пароль успешно сменен!");
    }

    @Override
    public ResponseEntity<String> changePasswordOfUser(ChangePasswordDTO changePasswordDTO, User user) {
        // Check if old password is correct
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("Старый пароль введен некорректно!");
        }
        // Check if new password and password confirmation equals
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())){
            return ResponseEntity.badRequest().body("Пароли не совпадают!");
        }

        // Set new password and save user
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Пароль успешно сменен!");
    }
}
