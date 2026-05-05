package com.bookstore.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data @AllArgsConstructor
public class CustomerProfileResponse {
    private Long id;
    private Long userId;
    private String fullName;
    private String phone;
    private List<AddressResponse> addresses;
}