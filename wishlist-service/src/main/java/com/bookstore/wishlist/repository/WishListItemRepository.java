package com.bookstore.wishlist.repository;

import com.bookstore.wishlist.entity.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {}