package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.review.AddAndUpdateReviewDto;
import com.example.LaptopKG.dto.review.GetReviewDto;
import com.example.LaptopKG.model.Review;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Role;
import com.example.LaptopKG.repository.LaptopRepository;
import com.example.LaptopKG.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.LaptopKG.dto.review.GetReviewDto.toGetReviewDtoList;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final LaptopRepository laptopRepository;

    //Review adding
    public ResponseEntity<String> addReview(AddAndUpdateReviewDto addAndUpdateReviewDto, User user){
        //Check if laptop exists in DB by ID
        if(!laptopRepository.existsById(addAndUpdateReviewDto.getLaptopId())){
            //if laptop does not exist, return bad request
            return ResponseEntity.badRequest().body("Laptop with id "
                    + addAndUpdateReviewDto.getLaptopId() + " wasn't found");
        }
        //Check if this user has already left review on this laptop
        if(reviewRepository.existsByLaptopIdAndUser(addAndUpdateReviewDto.getLaptopId(), user)){
            return ResponseEntity.badRequest().body("This user has already left review for the laptop with id "
                    + addAndUpdateReviewDto.getLaptopId());
        }

        //Take info from DTO and save review
        reviewRepository.save(
                Review.builder()
                        .score(addAndUpdateReviewDto.getScore())
                        .text(addAndUpdateReviewDto.getText())
                        .laptop(laptopRepository.findById(addAndUpdateReviewDto.getLaptopId()).get())
                        .user(user)
                        .build()
        );

        //If everything is ok, return new review
        return ResponseEntity.ok("Review was added");
    }

    //Get reviews by laptop id
    public List<GetReviewDto> getReviewsByLaptopId(long id) {
        //Return list of reviews on certain laptop
        return toGetReviewDtoList(reviewRepository.findAllByLaptopId(id));
    }

    //Review updating
    public ResponseEntity<String> updateReview(long id,
                                               AddAndUpdateReviewDto updateReviewDto,
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
