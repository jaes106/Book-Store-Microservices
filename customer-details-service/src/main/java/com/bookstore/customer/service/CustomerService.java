package com.bookstore.customer.service;

import com.bookstore.customer.dto.*;
import com.bookstore.customer.entity.*;
import com.bookstore.customer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerProfileRepository profileRepository;
    private final AddressRepository addressRepository;

    public CustomerProfile getProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public CustomerProfile createProfile(Long userId, CustomerProfileRequest req) {
        CustomerProfile profile = CustomerProfile.builder()
                .userId(userId).fullName(req.getFullName()).phone(req.getPhone()).build();
        return profileRepository.save(profile);
    }

    public CustomerProfile updateProfile(Long userId, CustomerProfileRequest req) {
        CustomerProfile profile = getProfile(userId);
        profile.setFullName(req.getFullName());
        profile.setPhone(req.getPhone());
        return profileRepository.save(profile);
    }

    public Address addAddress(Long userId, AddressRequest req) {
        CustomerProfile profile = getProfile(userId);
        if (req.isDefault()) {
            profile.getAddresses().forEach(a -> a.setDefault(false));
        }
        Address address = Address.builder()
                .customerProfile(profile).street(req.getStreet()).city(req.getCity())
                .state(req.getState()).pincode(req.getPincode()).country(req.getCountry())
                .isDefault(req.isDefault()).build();
        return addressRepository.save(address);
    }

    public List<Address> getAddresses(Long userId) {
        CustomerProfile profile = getProfile(userId);
        return addressRepository.findByCustomerProfileId(profile.getId());
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    public Address setDefaultAddress(Long userId, Long addressId) {
        CustomerProfile profile = getProfile(userId);
        profile.getAddresses().forEach(a -> a.setDefault(false));
        profileRepository.save(profile);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setDefault(true);
        return addressRepository.save(address);
    }
}