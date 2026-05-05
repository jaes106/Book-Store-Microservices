package com.bookstore.cart.service;

import com.bookstore.cart.client.ProductServiceClient;
import com.bookstore.cart.dto.*;
import com.bookstore.cart.model.*;
import com.bookstore.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    public Cart getCart(Long userId) {
        return cartRepository.findById(String.valueOf(userId))
                .orElse(Cart.builder().userId(String.valueOf(userId)).items(new ArrayList<>())
                        .totalAmount(BigDecimal.ZERO).build());
    }

    public Cart addToCart(Long userId, AddToCartRequest req) {
        ProductResponse product = productServiceClient.getProductById(req.getProductId());
        Cart cart = getCart(userId);

        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(req.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.setQuantity(i.getQuantity() + req.getQuantity()),
                        () -> cart.getItems().add(CartItem.builder()
                                .productId(product.getId())
                                .productTitle(product.getTitle())
                                .quantity(req.getQuantity())
                                .unitPrice(product.getPrice())
                                .build()));

        cart.recalculateTotal();
        return cartRepository.save(cart);
    }

    public Cart updateItem(Long userId, UpdateCartRequest req) {
        Cart cart = getCart(userId);
        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(req.getProductId()))
                .findFirst()
                .ifPresent(i -> i.setQuantity(req.getQuantity()));
        cart.recalculateTotal();
        return cartRepository.save(cart);
    }

    public Cart removeItem(Long userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        cart.recalculateTotal();
        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    public BigDecimal getTotal(Long userId) {
        return getCart(userId).getTotalAmount();
    }
}