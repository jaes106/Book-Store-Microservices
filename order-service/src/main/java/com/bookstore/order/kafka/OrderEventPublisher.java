package com.bookstore.order.kafka;

import com.bookstore.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderPlaced(Order order) {
        kafkaTemplate.send("order-events",
                new OrderEvent(order.getId(), order.getUserId(), "ORDER_PLACED", LocalDateTime.now()));
    }

    public void publishOrderStatusChanged(Order order) {
        kafkaTemplate.send("order-events",
                new OrderEvent(order.getId(), order.getUserId(), "ORDER_" + order.getStatus().name(), LocalDateTime.now()));
    }
}