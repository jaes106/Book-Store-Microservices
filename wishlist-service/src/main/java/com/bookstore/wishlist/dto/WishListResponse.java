package com.bookstore.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data @AllArgsConstructor
public class WishListResponse {
    private Long id;
    private Long userId;
    private List<WishListItemDto> items;
}