package com.example.LaptopKG.controller;

import com.example.LaptopKG.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/upload/laptop/{laptopId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление фото ноутбука"
    )
    public ResponseEntity<String> saveLaptopImage(@PathVariable("laptopId") Long laptopId,
                                                  @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForLaptop(laptopId, file);
    }
}
