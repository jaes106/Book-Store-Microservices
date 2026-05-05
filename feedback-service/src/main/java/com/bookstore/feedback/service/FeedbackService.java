package com.bookstore.feedback.service;

import com.bookstore.feedback.dto.*;
import com.bookstore.feedback.entity.Review;
import com.bookstore.feedback.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ReviewRepository reviewRepository;

    public FeedbackResponse submitReview(Long userId, String email, FeedbackRequest req) {
        Review review = Review.builder()
                .productId(req.getProductId()).userId(userId).userEmail(email)
                .rating(req.getRating()).comment(req.getComment()).build();
        return toResponse(reviewRepository.save(review));
    }

    public List<FeedbackResponse> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId).stream().map(this::toResponse).toList();
    }

    public Double getAverageRating(Long productId) {
        Double avg = reviewRepository.getAverageRating(productId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }

    public FeedbackResponse editReview(Long id, Long userId, FeedbackRequest req) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUserId().equals(userId)) throw new IllegalArgumentException("Unauthorized");
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        return toResponse(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private FeedbackResponse toResponse(Review r) {
        return new FeedbackResponse(r.getId(), r.getProductId(), r.getUserId(),
                r.getUserEmail(), r.getRating(), r.getComment(), r.getCreatedAt());
    }
}