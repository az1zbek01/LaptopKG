package com.example.LaptopKG.controller;

import com.example.LaptopKG.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload/laptop/{laptopId}")
    public ResponseEntity<String> saveLaptopImage(@PathVariable("laptopId") Long laptopId,
                                                  @RequestPart MultipartFile file) throws IOException {
        return imageService.saveForLaptop(laptopId, file);
    }
}
