package com.bookstore.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/products")
public interface ProductServiceClient {
    @PutMapping("/{id}/decrease-stock")
    void decreaseStock(@PathVariable Long id, @RequestParam int quantity);
}