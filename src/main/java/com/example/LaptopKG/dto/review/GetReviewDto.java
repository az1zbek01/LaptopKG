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
public class GetReviewDto {
    long id;

    String text;

    int score;

    private LocalDateTime updatedAt;

    GetUserDto userDto;

    public static GetReviewDto toGetReviewDto(Review review){
        return GetReviewDto.builder()
                .id(review.getId())
                .userDto(GetUserDto.getUserDto(review.getUser()))
                .score(review.getScore())
                .text(review.getText())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public static List<GetReviewDto> toGetReviewDtoList(List<Review> reviews){
        return reviews.stream().map(GetReviewDto::toGetReviewDto).collect(Collectors.toList());
    }

}
