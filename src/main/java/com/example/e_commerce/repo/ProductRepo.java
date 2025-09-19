package com.example.e_commerce.repo;

import com.example.e_commerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
