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

    //Review adding
    public ResponseEntity<String> addReview(RequestReviewDTO requestReviewDTO, User user){
        //Check if this user has already left review on this laptop
        if(reviewRepository.existsByLaptopIdAndUser(requestReviewDTO.getLaptopId(), user)){
            return ResponseEntity.badRequest().body("This user has already left review for the laptop with id "
                    + requestReviewDTO.getLaptopId());
        }

        Laptop laptop = laptopRepository.findById(requestReviewDTO.getLaptopId()).
                orElseThrow(
                        () -> new NotFoundException("Laptop with id " +
                                requestReviewDTO.getLaptopId() + " wasn't found")
                );

        //Take info from DTO and save review
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

        //If everything is ok, return new review
        return ResponseEntity.ok("Review was added");
    }

    //Get reviews by laptop id
    public List<ResponseReviewDTO> getReviewsByLaptopId(Long id) {
        // Check if laptop exists
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(
                () -> new NotFoundException("Laptop with id " + id + " wasn't found")
        );
        if(laptop.getStatus() == Status.DELETED){
            throw new NotFoundException("Laptop with id " + id + " wasn't found");
        }

        //Return list of reviews on certain laptop
        return toGetReviewDtoList(reviewRepository.findAllByLaptopId(id));
    }

    //Review updating
    public ResponseEntity<String> updateReview(long id,
                                               RequestReviewDTO updateReviewDto,
                                               User user) {

        //Check if review exists by ID
        if(!reviewRepository.existsById(id)){
            return ResponseEntity.badRequest().body("Review with id " + id + " wasn't found");
        }

        //Find review in DB and get it
        Review review = reviewRepository.findById(id).get();

        //Check If USER is not the owner of the review and is not ADMIN and return bad request
        if(!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != Role.ROLE_ADMIN){
            return ResponseEntity.badRequest().body("Only review owner can update review");
        }

        //If USER is the owner of the review or admin, then we take info from DTO and save Review
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

    //Review deleting
    public ResponseEntity<String> deleteReview(long id, User user) {
        // Check if review exists by id
        if(!reviewRepository.existsById(id)){
            // If review doesn't exist we return bad request
            return ResponseEntity.badRequest().body("Review with id " + id + " wasn't found");
        }
        // If review exists, we get it from DB
        Review review = reviewRepository.findById(id).get();

        // Check If USER is not the owner of the review and is not ADMIN and return bad request
        if(!review.getUser().getEmail().equals(user.getEmail()) && user.getRole() != (Role.ROLE_ADMIN)){
            return ResponseEntity.badRequest().body("Only review owner can delete review");
        }

        // If everything is ok, we delete review and return status 200
        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Review was deleted");
    }
}
