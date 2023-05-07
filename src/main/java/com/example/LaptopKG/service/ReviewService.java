package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.review.RequestReviewDTO;
import com.example.LaptopKG.dto.review.ResponseReviewDTO;
import com.example.LaptopKG.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    ResponseEntity<String> addReview(RequestReviewDTO requestReviewDTO, User user);
    List<ResponseReviewDTO> getReviewsByLaptopId(Long id);
    ResponseEntity<String> updateReview(Long id, RequestReviewDTO updateReviewDto, User user);
    ResponseEntity<String> deleteReview(Long id, User user);


}
