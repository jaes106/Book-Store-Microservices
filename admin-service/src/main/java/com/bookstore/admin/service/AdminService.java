package com.bookstore.admin.service;

import com.bookstore.admin.dto.*;
import com.bookstore.admin.entity.Admin;
import com.bookstore.admin.exception.ResourceNotFoundException;
import com.bookstore.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminResponse createAdmin(AdminRegistrationRequest req) {
        if (adminRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        Admin admin = Admin.builder()
                .name(req.getName())
                .email(req.getEmail())
                .role(req.getRole())
                .build();
        Admin saved = adminRepository.save(admin);
        return toResponse(saved);
    }

    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream().map(this::toResponse).toList();
    }

    private AdminResponse toResponse(Admin a) {
        return new AdminResponse(a.getId(), a.getName(), a.getEmail(), a.getRole(), a.getCreatedAt());
    }
}