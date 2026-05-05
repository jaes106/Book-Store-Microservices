package com.bookstore.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class WishListItemDto {
    private Long productId;
    private String productTitle;
    private LocalDateTime addedAt;
}