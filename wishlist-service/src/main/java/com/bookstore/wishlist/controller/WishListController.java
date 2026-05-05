package com.bookstore.wishlist.controller;

import com.bookstore.wishlist.entity.WishList;
import com.bookstore.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Tag(name = "WishList", description = "Wish list APIs")
public class WishListController {

    private final WishListService wishListService;

    private Long getUserId(HttpServletRequest req) {
        return Long.valueOf(req.getHeader("X-User-Id"));
    }

    @GetMapping
    public ResponseEntity<WishList> getWishList(HttpServletRequest req) {
        return ResponseEntity.ok(wishListService.getWishList(getUserId(req)));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<WishList> addProduct(HttpServletRequest req,
                                               @PathVariable Long productId,
                                               @RequestParam(required = false) String title) {
        return ResponseEntity.ok(wishListService.addProduct(getUserId(req), productId, title));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<WishList> removeProduct(HttpServletRequest req, @PathVariable Long productId) {
        return ResponseEntity.ok(wishListService.removeProduct(getUserId(req), productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<WishList> clearWishList(HttpServletRequest req) {
        return ResponseEntity.ok(wishListService.clearWishList(getUserId(req)));
    }
}