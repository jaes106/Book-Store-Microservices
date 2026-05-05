package com.bookstore.wishlist.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist_items")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;

    @Column(nullable = false)
    private Long productId;

    private String productTitle;
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() { this.addedAt = LocalDateTime.now(); }
}