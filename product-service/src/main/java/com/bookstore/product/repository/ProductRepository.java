package com.bookstore.product.repository;

import com.bookstore.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%',:q,'%')) " +
            "OR LOWER(p.author) LIKE LOWER(CONCAT('%',:q,'%'))")
    List<Product> search(@Param("q") String q);

    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findAll(Pageable pageable);
}