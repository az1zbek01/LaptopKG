package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.user.ChangePasswordDTO;
import com.example.LaptopKG.dto.user.ResetPasswordDTO;
import com.example.LaptopKG.model.User;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    ResponseEntity<String> forgotPassword(String userEmail);
    ResponseEntity<String> setNewPassword(String token, ResetPasswordDTO password);
    ResponseEntity<String> changePasswordOfUser(ChangePasswordDTO changePasswordDTO, User user);
}
