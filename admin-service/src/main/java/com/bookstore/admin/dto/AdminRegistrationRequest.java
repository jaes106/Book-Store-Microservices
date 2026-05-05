package com.bookstore.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AdminRegistrationRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank private String role;
}