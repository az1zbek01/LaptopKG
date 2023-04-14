package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<GetUserDto> getUserDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new GetUserDto().getUserDto(user));
    }

    @GetMapping("/Hello")
    public ResponseEntity<String> getUserDtoResponseEntity(){
        return ResponseEntity.ok("Okay");
    }

}
