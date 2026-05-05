package com.bookstore.notification.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserEvent {
    private Long userId;
    private String email;
    private String type;
    private LocalDateTime timestamp;
}