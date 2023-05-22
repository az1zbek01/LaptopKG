package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.review.RequestReviewDTO;
import com.example.LaptopKG.dto.review.ResponseReviewDTO;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.Review;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Role;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.LaptopRepository;
import com.example.LaptopKG.repository.ReviewRepository;
import com.example.LaptopKG.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.LaptopKG.dto.review.ResponseReviewDTO.toGetReviewDtoList;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final LaptopRepository laptopRepository;

    public List<ResponseReviewDTO> getReviewsByLaptopId(Long id) {
        Laptop laptop = findLaptopById(id);
        return toGetReviewDtoList(reviewRepository.findAllByLaptop(laptop));
    }

    public ResponseEntity<String> addReview(RequestReviewDTO requestReviewDTO, User user) {
        if (reviewRepository.existsByLaptopIdAndUser(requestReviewDTO.getLaptopId(), user)) {
            return ResponseEntity.badRequest().body("Этот пользователь уже оставил отзыв на ноутбук с айди "
                    + requestReviewDTO.getLaptopId());
        }

        Laptop laptop = findLaptopById(requestReviewDTO.getLaptopId());
        reviewRepository.save(constructReview(requestReviewDTO, user, laptop));

        updateLaptopAverageScore(laptop);

        return ResponseEntity.ok("Отзыв успешно добавлен");
    }

    public ResponseEntity<String> updateReview(Long id,
                                               RequestReviewDTO updateReviewDto,
                                               User user) {
        Review review = findReviewById(id);

        if (!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != Role.ROLE_ADMIN) {
            return ResponseEntity.badRequest().body("Только пользователь, написавший отзыв, может обновить его");
        }

        review.setScore(updateReviewDto.getScore());
        review.setText(updateReviewDto.getText());
        reviewRepository.save(review);

        Laptop laptop = findLaptopById(review.getLaptop().getId());
        updateLaptopAverageScore(laptop);

        return ResponseEntity.ok("Отзыв успешно обновлен");
    }

    public ResponseEntity<String> deleteReview(Long id, User user) {
        Review review = findReviewById(id);

        if (!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != (Role.ROLE_ADMIN)) {
            return ResponseEntity.badRequest().body("Только пользователь, написавший отзыв, может удалить его");
        }

        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Отзыв успешно удален");
    }

    private Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Отзыв с айди " + id + " не найден")
                );
    }

    private Laptop findLaptopById(Long laptopId) {
        return laptopRepository.findById(laptopId)
                .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Ноутбук с айди" + laptopId + " не найден")
                );
    }

    private Review constructReview(RequestReviewDTO requestReviewDTO, User user, Laptop laptop) {
        return Review.builder()
                .score(requestReviewDTO.getScore())
                .text(requestReviewDTO.getText())
                .laptop(laptop)
                .user(user)
                .build();
    }

    private void updateLaptopAverageScore(Laptop laptop) {
        List<Review> reviews = reviewRepository.findAllByLaptop(laptop);
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getScore();
        }
        laptop.setAverageScore(Double.parseDouble(new DecimalFormat("0.0").format(sum / reviews.size())
                .replaceAll(",", ".")));
        laptopRepository.save(laptop);
    }
}
