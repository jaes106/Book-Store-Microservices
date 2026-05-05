package com.bookstore.cart.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateCartRequest {
    @NotNull private Long productId;
    @Min(1) private int quantity;
}