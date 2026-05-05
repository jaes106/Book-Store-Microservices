package com.bookstore.order.client;

import com.bookstore.order.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CART-SERVICE")
public interface CartServiceClient {
    @GetMapping("/api/cart")
    CartResponse getCart(@RequestHeader("X-User-Id") String userId);

    @DeleteMapping("/api/cart/clear")
    void clearCart(@RequestHeader("X-User-Id") String userId);
}