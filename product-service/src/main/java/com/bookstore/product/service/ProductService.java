package com.bookstore.product.service;

import com.bookstore.product.dto.*;
import com.bookstore.product.entity.*;
import com.bookstore.product.exception.ResourceNotFoundException;
import com.bookstore.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductResponse> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size)).map(this::toResponse);
    }

    public ProductResponse getProductById(Long id) {
        return toResponse(findProduct(id));
    }

    public List<ProductResponse> searchProducts(String q) {
        return productRepository.search(q).stream().map(this::toResponse).toList();
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream().map(this::toResponse).toList();
    }

    public ProductResponse createProduct(ProductRequest req) {
        Category category = req.getCategoryId() != null
                ? categoryRepository.findById(req.getCategoryId()).orElse(null) : null;
        Product product = Product.builder()
                .title(req.getTitle()).author(req.getAuthor()).isbn(req.getIsbn())
                .price(req.getPrice()).stockQuantity(req.getStockQuantity())
                .imageUrl(req.getImageUrl()).category(category).build();
        return toResponse(productRepository.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest req) {
        Product product = findProduct(id);
        product.setTitle(req.getTitle());
        product.setAuthor(req.getAuthor());
        product.setIsbn(req.getIsbn());
        product.setPrice(req.getPrice());
        product.setStockQuantity(req.getStockQuantity());
        product.setImageUrl(req.getImageUrl());
        if (req.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(req.getCategoryId()).orElse(null));
        }
        return toResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) throw new ResourceNotFoundException("Product not found");
        productRepository.deleteById(id);
    }

    public void decreaseStock(Long id, int quantity) {
        Product product = findProduct(id);
        if (product.getStockQuantity() < quantity) throw new IllegalArgumentException("Insufficient stock");
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toCategoryResponse).toList();
    }

    public CategoryResponse createCategory(CategoryRequest req) {
        Category category = Category.builder().name(req.getName()).description(req.getDescription()).build();
        return toCategoryResponse(categoryRepository.save(category));
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private ProductResponse toResponse(Product p) {
        CategoryResponse cat = p.getCategory() != null ? toCategoryResponse(p.getCategory()) : null;
        return new ProductResponse(p.getId(), p.getTitle(), p.getAuthor(), p.getIsbn(),
                p.getPrice(), p.getStockQuantity(), p.getImageUrl(), cat, p.getCreatedAt());
    }

    private CategoryResponse toCategoryResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getDescription());
    }
}