package com.bookstore.notification.service;

import com.bookstore.notification.kafka.OrderEvent;
import com.bookstore.notification.kafka.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@bookstore.com}")
    private String from;

    public void sendOrderConfirmation(OrderEvent event) {
        send("Order Confirmed — #" + event.getOrderId(),
                "Your order #" + event.getOrderId() + " has been confirmed.",
                event.getUserId().toString());
        log.info("Order confirmation sent for order {}", event.getOrderId());
    }

    public void sendShippingUpdate(OrderEvent event) {
        send("Order Shipped — #" + event.getOrderId(),
                "Your order #" + event.getOrderId() + " has been shipped!",
                event.getUserId().toString());
        log.info("Shipping update sent for order {}", event.getOrderId());
    }

    public void sendDeliveryConfirmation(OrderEvent event) {
        send("Order Delivered — #" + event.getOrderId(),
                "Your order #" + event.getOrderId() + " has been delivered. Enjoy your books!",
                event.getUserId().toString());
        log.info("Delivery confirmation sent for order {}", event.getOrderId());
    }

    public void sendWelcomeEmail(UserEvent event) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(event.getEmail());
            msg.setSubject("Welcome to Bookstore!");
            msg.setText("Hello! Welcome to the Bookstore. Your account has been created successfully.");
            mailSender.send(msg);
            log.info("Welcome email sent to {}", event.getEmail());
        } catch (Exception e) {
            log.error("Failed to send welcome email: {}", e.getMessage());
        }
    }

    private void send(String subject, String text, String userId) {
        log.info("Sending email to user {} | Subject: {}", userId, subject);
    }
}