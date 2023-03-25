package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.model.User;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GetUserDto> getUserDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new GetUserDto().getUserDto(user));
    }
    @GetMapping("/getAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GetUserDto> getAdminDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new GetUserDto().getUserDto(user));
    }

}
