package com.bookstore.admin.controller;

import com.bookstore.admin.client.OrderServiceClient;
import com.bookstore.admin.client.ProductServiceClient;
import com.bookstore.admin.client.UserServiceClient;
import com.bookstore.admin.dto.*;
import com.bookstore.admin.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {

    private final AdminService adminService;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final UserServiceClient userServiceClient;

    @PostMapping("/register")
    public ResponseEntity<AdminResponse> registerAdmin(@Valid @RequestBody AdminRegistrationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAdmin(req));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        return ResponseEntity.ok(userServiceClient.getAllUsers());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest req) {
        return ResponseEntity.ok(productServiceClient.updateProduct(id, req));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productServiceClient.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Map<String, Object>>> getAllOrders() {
        return ResponseEntity.ok(orderServiceClient.getAllOrders());
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long id,
                                                                 @RequestBody OrderStatusRequest req) {
        return ResponseEntity.ok(orderServiceClient.updateOrderStatus(id, req));
    }
}