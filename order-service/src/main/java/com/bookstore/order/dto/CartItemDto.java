package com.bookstore.order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;
}