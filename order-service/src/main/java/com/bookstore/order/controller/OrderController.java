package com.bookstore.order.controller;

import com.bookstore.order.dto.*;
import com.bookstore.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    private Long getUserId(HttpServletRequest req) {
        return Long.valueOf(req.getHeader("X-User-Id"));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(HttpServletRequest req,
                                                    @Valid @RequestBody OrderRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(getUserId(req), body));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(HttpServletRequest req) {
        return ResponseEntity.ok(orderService.getUserOrders(getUserId(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(HttpServletRequest req, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id, getUserId(req)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
                                                      @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(orderService.updateStatus(id, body.get("status")));
    }
}