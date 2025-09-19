package com.example.e_commerce.service.impl;

import com.example.e_commerce.dto.product.ProductCreateDto;
import com.example.e_commerce.exception.DuplicateProductException;
import com.example.e_commerce.exception.ProductNotFoundException;
import com.example.e_commerce.mapper.ProductMapper;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.repo.ProductRepo;
import com.example.e_commerce.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepo repo;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepo repo, ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Product create(ProductCreateDto dto) {
        if (repo.existsByName(dto.name())) {
            throw new DuplicateProductException("Product already exists: " + dto.name());
        }
        var entity = mapper.toEntity(dto);
        return repo.save(entity);
    }

    @Override
    public Product getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }
}
