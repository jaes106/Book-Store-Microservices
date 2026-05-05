package com.bookstore.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerProfileRequest {
    @NotBlank private String fullName;
    private String phone;
}