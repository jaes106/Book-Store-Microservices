package com.bookstore.order.service;

import com.bookstore.order.client.CartServiceClient;
import com.bookstore.order.dto.*;
import com.bookstore.order.entity.*;
import com.bookstore.order.kafka.OrderEventPublisher;
import com.bookstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;
    private final OrderEventPublisher eventPublisher;

    public OrderResponse placeOrder(Long userId, OrderRequest req) {
        CartResponse cart = cartServiceClient.getCart(String.valueOf(userId));
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = Order.builder()
                .userId(userId)
                .shippingAddress(req.getShippingAddress())
                .totalAmount(cart.getTotalAmount())
                .build();

        List<OrderItem> items = cart.getItems().stream().map(ci ->
                OrderItem.builder()
                        .order(order)
                        .productId(ci.getProductId())
                        .productTitle(ci.getProductTitle())
                        .quantity(ci.getQuantity())
                        .unitPrice(ci.getUnitPrice())
                        .subtotal(ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                        .build()).toList();

        order.setItems(items);
        Order saved = orderRepository.save(order);

        cartServiceClient.clearCart(String.valueOf(userId));
        eventPublisher.publishOrderPlaced(saved);

        return toResponse(saved);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public OrderResponse getOrderById(Long id) {
        return toResponse(findOrder(id));
    }

    public OrderResponse cancelOrder(Long id, Long userId) {
        Order order = findOrder(id);
        if (!order.getUserId().equals(userId)) throw new IllegalArgumentException("Unauthorized");
        if (order.getStatus() != OrderStatus.PENDING) throw new IllegalArgumentException("Cannot cancel order in current status");
        order.setStatus(OrderStatus.CANCELLED);
        Order saved = orderRepository.save(order);
        eventPublisher.publishOrderStatusChanged(saved);
        return toResponse(saved);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderResponse updateStatus(Long id, String status) {
        Order order = findOrder(id);
        order.setStatus(OrderStatus.valueOf(status));
        Order saved = orderRepository.save(order);
        eventPublisher.publishOrderStatusChanged(saved);
        return toResponse(saved);
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private OrderResponse toResponse(Order o) {
        List<OrderItemDto> itemDtos = o.getItems().stream()
                .map(i -> new OrderItemDto(i.getProductId(), i.getProductTitle(),
                        i.getQuantity(), i.getUnitPrice(), i.getSubtotal())).toList();
        return new OrderResponse(o.getId(), o.getUserId(), o.getStatus(),
                o.getTotalAmount(), o.getShippingAddress(), itemDtos, o.getCreatedAt());
    }
}