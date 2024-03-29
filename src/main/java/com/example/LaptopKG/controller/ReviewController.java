package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.review.RequestReviewDTO;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.implementations.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с отзывами",
        description = "В этом контроллере есть возможности добавления, обновления и удаления отзывов"
)
public class ReviewController {
    private final ReviewServiceImpl reviewServiceImpl;

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление отзыва к ноутбуку"
    )
    public ResponseEntity<String> addReview(@RequestBody @Valid RequestReviewDTO addReviewDto,
                                            @AuthenticationPrincipal User user) {
        return reviewServiceImpl.addReview(addReviewDto, user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение отзыва"
    )
    public ResponseEntity<String> updateReview(@PathVariable Long id,
                                               @RequestBody @Valid RequestReviewDTO updateReviewDto,
                                               @AuthenticationPrincipal User user) {
        return reviewServiceImpl.updateReview(id, updateReviewDto, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление отзыва"
    )
    public ResponseEntity<String> deleteReview(@PathVariable Long id,
                                               @AuthenticationPrincipal User user) {
        return reviewServiceImpl.deleteReview(id, user);
    }
}
