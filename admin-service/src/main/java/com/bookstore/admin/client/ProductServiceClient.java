package com.bookstore.admin.client;

import com.bookstore.admin.dto.ProductRequest;
import com.bookstore.admin.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/products")
public interface ProductServiceClient {

    @PutMapping("/{id}")
    ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest req);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Long id);
}