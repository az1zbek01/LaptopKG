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

    public ResponseEntity<String> addReview(RequestReviewDTO requestReviewDTO, User user){
        if(reviewRepository.existsByLaptopIdAndUser(requestReviewDTO.getLaptopId(), user)){
            return ResponseEntity.badRequest().body("This user has already left review for the laptop with id "
                    + requestReviewDTO.getLaptopId());
        }

        Laptop laptop = laptopRepository.findById(requestReviewDTO.getLaptopId()).
                orElseThrow(
                        () -> new NotFoundException("Laptop with id " +
                                requestReviewDTO.getLaptopId() + " wasn't found")
                );

        reviewRepository.save(
                Review.builder()
                        .score(requestReviewDTO.getScore())
                        .text(requestReviewDTO.getText())
                        .laptop(laptop)
                        .user(user)
                        .build()
        );

        List<Review> reviews = reviewRepository.findAllByLaptopId(laptop.getId());
        double sum = 0;
        for(Review review:reviews){
            sum+=review.getScore();
        }
        laptop.setAverageScore(Double.parseDouble(new DecimalFormat("0.0").format(sum/reviews.size())
                .replaceAll(",", ".")));
        laptopRepository.save(laptop);

        return ResponseEntity.ok("Review was added");
    }

    public List<ResponseReviewDTO> getReviewsByLaptopId(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(
                () -> new NotFoundException("Laptop with id " + id + " wasn't found")
        );
        if(laptop.getStatus() == Status.DELETED){
            throw new NotFoundException("Laptop with id " + id + " wasn't found");
        }

        return toGetReviewDtoList(reviewRepository.findAllByLaptopId(id));
    }

    public ResponseEntity<String> updateReview(long id,
                                               RequestReviewDTO updateReviewDto,
                                               User user) {

        if(!reviewRepository.existsById(id)){
            return ResponseEntity.badRequest().body("Review with id " + id + " wasn't found");
        }

        Review review = reviewRepository.findById(id).get();

        if(!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != Role.ROLE_ADMIN){
            return ResponseEntity.badRequest().body("Only review owner can update review");
        }

        review.setScore(updateReviewDto.getScore());
        review.setText(updateReviewDto.getText());
        reviewRepository.save(review);

        Laptop laptop = laptopRepository.findById(review.getLaptop().getId()).get();
        List<Review> reviews = reviewRepository.findAllByLaptopId(laptop.getId());
        double sum = 0;
        for(Review review1:reviews){
            sum+=review1.getScore();
        }
        laptop.setAverageScore(Double.parseDouble(new DecimalFormat("0.0").format(sum/reviews.size())
                .replaceAll(",", ".")));
        laptopRepository.save(laptop);

        return ResponseEntity.ok("Review was successfully updated");
    }

    public ResponseEntity<String> deleteReview(long id, User user) {
        if(!reviewRepository.existsById(id)){
            return ResponseEntity.badRequest().body("Review with id " + id + " wasn't found");
        }
        Review review = reviewRepository.findById(id).get();

        if(!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != (Role.ROLE_ADMIN)){
            return ResponseEntity.badRequest().body("Only review owner can delete review");
        }

        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Review was deleted");
    }
}
