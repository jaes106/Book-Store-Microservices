package com.bookstore.feedback.controller;

import com.bookstore.feedback.dto.*;
import com.bookstore.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Product review APIs")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponse> submit(HttpServletRequest req,
                                                   @Valid @RequestBody FeedbackRequest body) {
        Long userId = Long.valueOf(req.getHeader("X-User-Id"));
        String email = req.getHeader("X-User-Email");
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.submitReview(userId, email, body));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<FeedbackResponse>> getProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getProductReviews(id));
    }

    @GetMapping("/product/{id}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getAverageRating(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> editReview(HttpServletRequest req,
                                                       @PathVariable Long id,
                                                       @Valid @RequestBody FeedbackRequest body) {
        Long userId = Long.valueOf(req.getHeader("X-User-Id"));
        return ResponseEntity.ok(feedbackService.editReview(id, userId, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        feedbackService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}