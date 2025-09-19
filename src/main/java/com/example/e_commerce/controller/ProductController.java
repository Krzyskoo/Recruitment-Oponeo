package com.example.e_commerce.controller;

import com.example.e_commerce.dto.product.ProductCreateDto;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductServiceImpl service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody ProductCreateDto dto) {
        Product p = service.create(dto);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrThrow(id));
    }
}
