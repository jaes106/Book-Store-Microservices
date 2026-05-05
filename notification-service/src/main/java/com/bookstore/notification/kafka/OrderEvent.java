package com.bookstore.notification.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String type;
    private LocalDateTime timestamp;
}