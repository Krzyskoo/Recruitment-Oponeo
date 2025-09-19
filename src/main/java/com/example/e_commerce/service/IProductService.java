package com.example.e_commerce.service;

import com.example.e_commerce.dto.product.ProductCreateDto;
import com.example.e_commerce.model.product.Product;

public interface IProductService {
    Product create(ProductCreateDto dto);
    Product getOrThrow(Long id);
}
