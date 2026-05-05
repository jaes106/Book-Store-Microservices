package com.bookstore.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WishListServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WishListServiceApplication.class, args);
    }
}