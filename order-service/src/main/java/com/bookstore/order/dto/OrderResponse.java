package com.bookstore.order.dto;

import com.bookstore.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;
}