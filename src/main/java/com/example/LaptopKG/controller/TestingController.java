package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TestingController {

    @GetMapping("/getUser")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<GetUserDto> getUserDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new GetUserDto().getUserDto(user));
    }
    @GetMapping("/getAdmin")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GetUserDto> getAdminDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new GetUserDto().getUserDto(user));
    }

}
