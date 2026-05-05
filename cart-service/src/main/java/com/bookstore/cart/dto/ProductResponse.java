package com.bookstore.cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer stockQuantity;
}