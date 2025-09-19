package com.example.e_commerce.mapper;

import com.example.e_commerce.dto.product.ProductCreateDto;
import com.example.e_commerce.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductCreateDto dto) {
        var p = new Product();
        p.setName(dto.name());
        p.setNetPrice(dto.netPrice());
        p.setVatRate(dto.vatRate());
        return p;
    }
}
