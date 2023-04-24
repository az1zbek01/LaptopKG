package com.example.LaptopKG.controller;

import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с фото",
        description = "В этом контроллеры есть возможности добавления фото"
)
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/upload/laptop/{laptopId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление фото ноутбука"
    )
    public ResponseEntity<String> saveLaptopImage(@PathVariable("laptopId") Long laptopId,
                                                  @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForLaptop(laptopId, file);
    }

    @PostMapping(value = "/upload/myAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление фото профиля"
    )
    public ResponseEntity<String> saveUserImage(@AuthenticationPrincipal User user,
                                                  @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForUser(user, file);
    }
}
