package com.example.admin.Controller;

import com.example.admin.Entity.Review;
import com.example.admin.Entity.User;
import com.example.admin.Service.ReviewService;
import com.example.admin.Service.UserService;
import com.example.admin.request.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService; // Add this to fetch the user

    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody Review review) {
        // Fetch the complete User entity from the database using the userId
        User user = userService.findUserById(review.getUser().getId());

        // Set the fetched user in the review object
        review.setUser(user);

        // Now save the review with the complete user object
        Review savedReview = reviewService.addReview(review);

        // Update the average rating of the product
        reviewService.updateProductAverageRating(savedReview.getProductId());


        // Create a response including the user's name and avatar
        ReviewResponse reviewResponse = new ReviewResponse(
                savedReview.getId(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getProductId(),
                user.getName(),
                user.getAvatarUrl() // Ensure the avatar is included in the response
        );

        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        List<ReviewResponse> response = reviews.stream().map(review -> new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getProductId(),
                review.getUser().getName(),
                review.getUser().getAvatarUrl() // Fetch user's avatar
        )).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getAverageRating(productId));
    }
}
