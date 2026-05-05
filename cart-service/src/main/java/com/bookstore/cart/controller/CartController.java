package com.bookstore.cart.controller;

import com.bookstore.cart.dto.*;
import com.bookstore.cart.model.Cart;
import com.bookstore.cart.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart APIs")
public class CartController {

    private final CartService cartService;

    private Long getUserId(jakarta.servlet.http.HttpServletRequest request) {
        return Long.valueOf(request.getHeader("X-User-Id"));
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.ok(cartService.getCart(getUserId(req)));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(jakarta.servlet.http.HttpServletRequest req,
                                          @Valid @RequestBody AddToCartRequest body) {
        return ResponseEntity.ok(cartService.addToCart(getUserId(req), body));
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateItem(jakarta.servlet.http.HttpServletRequest req,
                                           @Valid @RequestBody UpdateCartRequest body) {
        return ResponseEntity.ok(cartService.updateItem(getUserId(req), body));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeItem(jakarta.servlet.http.HttpServletRequest req,
                                           @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItem(getUserId(req), productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(jakarta.servlet.http.HttpServletRequest req) {
        cartService.clearCart(getUserId(req));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal(jakarta.servlet.http.HttpServletRequest req) {
        return ResponseEntity.ok(cartService.getTotal(getUserId(req)));
    }
}