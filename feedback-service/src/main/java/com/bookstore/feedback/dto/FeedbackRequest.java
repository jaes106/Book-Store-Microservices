package com.bookstore.feedback.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull private Long productId;
    @Min(1) @Max(5) private int rating;
    private String comment;
}