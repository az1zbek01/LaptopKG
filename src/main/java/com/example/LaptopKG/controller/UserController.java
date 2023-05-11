package com.example.LaptopKG.controller;


import com.example.LaptopKG.dto.user.ChangeEmailDTO;
import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.dto.user.UpdateUserDto;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.implementations.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserServiceImpl userService;

    @SecurityRequirement(name = "JWT")
    @GetMapping("/myInfo")
    @Operation(
            summary = "Получение информации пользователя"
    )
    public ResponseEntity<GetUserDto> getUserDtoResponseEntity(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(GetUserDto.getUserDto(user));
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/changeInfo")
    @Operation(
            summary = "Изменение данных пользователя"
    )
    public GetUserDto changeUserInfo(@RequestBody UpdateUserDto userDto,
                                                 @AuthenticationPrincipal User user){
        return userService.changeUserInfo(userDto, user);
    }

}
