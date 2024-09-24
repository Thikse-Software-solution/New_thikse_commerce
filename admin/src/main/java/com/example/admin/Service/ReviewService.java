package com.example.admin.Service;


import com.example.admin.Entity.Product;
import com.example.admin.Entity.Review;
import com.example.admin.Entity.User;
import com.example.admin.Repository.ProductRepository;
import com.example.admin.Repository.ReviewRepository;
import com.example.admin.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;


    @Autowired
    private UserRepository userRepository; // Inject UserRepository to fetch user

    public Review addReview(Review review) {
        // Fetch the full User object using the userId from the request
        User user = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the full user object in the review
        review.setUser(user);
        updateProductAverageRating(review.getProductId());

        // Save the review
        return reviewRepository.save(review);
    }


    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
    public void updateProductAverageRating(Long productId) {
        // Fetch all reviews for the product
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (!reviews.isEmpty()) {
            // Calculate average rating
            double averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            // Log the calculated average rating for debugging
            System.out.println("Calculated average rating: " + averageRating);

            // Fetch the product by ID
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Log the product details
            System.out.println("Product found: " + product.getName());

            // Set and save the updated average rating
            product.setAverageRating(averageRating);
            productRepository.save(product);  // Save the updated product

            // Log success message
            System.out.println("Average rating updated for product: " + product.getName());
        } else {
            // Log if no reviews are found
            System.out.println("No reviews found for product ID: " + productId);
        }
    }


}
