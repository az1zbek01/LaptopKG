package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.review.AddAndUpdateReviewDto;
import com.example.LaptopKG.dto.review.GetReviewDto;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
@Tag(
        name = "Контроллер для работы с отзывами",
        description = "В этом контроллере есть возможности добавления, обновления и удаления отзывов"
)
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление отзыва к ноутбуку"
    )
    public ResponseEntity<String> addReview(@RequestBody AddAndUpdateReviewDto addReviewDto,
                                            @AuthenticationPrincipal User user){
        return reviewService.addReview(addReviewDto, user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Изменение отзыва"
    )
    public ResponseEntity<String> updateReview(@PathVariable long id,
                                               @RequestBody AddAndUpdateReviewDto updateReviewDto,
                                               @AuthenticationPrincipal User user){
        return reviewService.updateReview(id, updateReviewDto, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление отзыва"
    )
    public ResponseEntity<String> deleteReview(@PathVariable long id,
                                               @AuthenticationPrincipal User user){
        return reviewService.deleteReview(id, user);
    }
}
