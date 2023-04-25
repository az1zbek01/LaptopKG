package com.example.LaptopKG.dto.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAndUpdateReviewDto {
    String text;

    int score;

    long laptopId;

}
