package com.bookstore.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class FeedbackResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String userEmail;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}