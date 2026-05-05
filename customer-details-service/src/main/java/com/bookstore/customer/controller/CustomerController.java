package com.bookstore.customer.controller;

import com.bookstore.customer.dto.*;
import com.bookstore.customer.entity.*;
import com.bookstore.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customer details APIs")
public class CustomerController {

    private final CustomerService customerService;

    private Long getUserId(HttpServletRequest req) {
        return Long.valueOf(req.getHeader("X-User-Id"));
    }

    @GetMapping("/details")
    public ResponseEntity<CustomerProfile> getDetails(HttpServletRequest req) {
        return ResponseEntity.ok(customerService.getProfile(getUserId(req)));
    }

    @PostMapping("/details")
    public ResponseEntity<CustomerProfile> createProfile(HttpServletRequest req,
                                                         @Valid @RequestBody CustomerProfileRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createProfile(getUserId(req), body));
    }

    @PutMapping("/details")
    public ResponseEntity<CustomerProfile> updateProfile(HttpServletRequest req,
                                                         @Valid @RequestBody CustomerProfileRequest body) {
        return ResponseEntity.ok(customerService.updateProfile(getUserId(req), body));
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> addAddress(HttpServletRequest req,
                                              @Valid @RequestBody AddressRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addAddress(getUserId(req), body));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses(HttpServletRequest req) {
        return ResponseEntity.ok(customerService.getAddresses(getUserId(req)));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        customerService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addresses/{id}/default")
    public ResponseEntity<Address> setDefault(HttpServletRequest req, @PathVariable Long id) {
        return ResponseEntity.ok(customerService.setDefaultAddress(getUserId(req), id));
    }
}