package com.example.LaptopKG.dto.review;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestReviewDTO {
    @Size(min = 5, max=100, message = "Отзыв должен содержать от 5 до 100 символов")
    String text;

    int score;

    long laptopId;

}
