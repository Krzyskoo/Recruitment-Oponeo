package com.example.e_commerce.service.impl;

import com.example.e_commerce.dto.customer.CustomerCreateDto;
import com.example.e_commerce.exception.CustomerNotFoundException;
import com.example.e_commerce.exception.EmailAlreadyExistException;
import com.example.e_commerce.exception.TaxIdAlreadyExistException;
import com.example.e_commerce.mapper.CustomerMapper;
import com.example.e_commerce.model.customer.Customer;
import com.example.e_commerce.repo.CustomerRepo;
import com.example.e_commerce.service.ICustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepo repo;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepo repo, CustomerMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Customer create(CustomerCreateDto dto) {
        if (repo.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistException("Email already exists: " + dto.email());
        }
        if (dto.taxId() != null && !dto.taxId().isBlank() && repo.existsByTaxId(dto.taxId())) {
            throw new TaxIdAlreadyExistException("Tax ID already exists: " + dto.taxId());
        }

        var entity = mapper.mapToCustomer(dto);
        return repo.save(entity);
    }

    @Override
    public Customer getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

    }
}
