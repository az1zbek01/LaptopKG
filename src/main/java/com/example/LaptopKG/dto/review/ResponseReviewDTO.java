package com.example.LaptopKG.dto.review;

import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.model.Review;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseReviewDTO {
    long id;

    String text;

    int score;

    LocalDateTime updatedAt;

    GetUserDto userDto;

    public static ResponseReviewDTO toGetReviewDto(Review review){
        return ResponseReviewDTO.builder()
                .id(review.getId())
                .userDto(GetUserDto.getUserDto(review.getUser()))
                .score(review.getScore())
                .text(review.getText())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public static List<ResponseReviewDTO> toGetReviewDtoList(List<Review> reviews){
        return reviews.stream().map(ResponseReviewDTO::toGetReviewDto).collect(Collectors.toList());
    }

}
