package com.bookstore.cart.client;

import com.bookstore.cart.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/products")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable Long id);
}