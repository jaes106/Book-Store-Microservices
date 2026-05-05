package com.bookstore.wishlist.service;

import com.bookstore.wishlist.entity.*;
import com.bookstore.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishList getWishList(Long userId) {
        return wishListRepository.findByUserId(userId)
                .orElseGet(() -> wishListRepository.save(
                        WishList.builder().userId(userId).build()));
    }

    public WishList addProduct(Long userId, Long productId, String productTitle) {
        WishList wishList = getWishList(userId);
        boolean exists = wishList.getItems().stream()
                .anyMatch(i -> i.getProductId().equals(productId));
        if (!exists) {
            wishList.getItems().add(WishListItem.builder()
                    .wishList(wishList).productId(productId).productTitle(productTitle).build());
            wishListRepository.save(wishList);
        }
        return wishList;
    }

    public WishList removeProduct(Long userId, Long productId) {
        WishList wishList = getWishList(userId);
        wishList.getItems().removeIf(i -> i.getProductId().equals(productId));
        return wishListRepository.save(wishList);
    }

    public WishList clearWishList(Long userId) {
        WishList wishList = getWishList(userId);
        wishList.getItems().clear();
        return wishListRepository.save(wishList);
    }
}