package com.bookstore.admin.client;

import com.bookstore.admin.dto.OrderStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ORDER-SERVICE", path = "/api/orders")
public interface OrderServiceClient {

    @GetMapping("/all")
    List<Map<String, Object>> getAllOrders();

    @PutMapping("/{id}/status")
    Map<String, Object> updateOrderStatus(@PathVariable Long id,
                                          @RequestBody OrderStatusRequest req);
}