package com.bookstore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private CategoryResponse category;
    private LocalDateTime createdAt;
}