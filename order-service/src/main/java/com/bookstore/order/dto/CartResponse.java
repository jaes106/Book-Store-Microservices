package com.bookstore.order.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private String userId;
    private List<CartItemDto> items;
    private BigDecimal totalAmount;
}