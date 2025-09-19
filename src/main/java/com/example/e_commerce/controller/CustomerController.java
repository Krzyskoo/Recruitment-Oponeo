package com.example.e_commerce.controller;

import com.example.e_commerce.dto.customer.CustomerCreateDto;
import com.example.e_commerce.dto.customer.CustomerDetailsDto;
import com.example.e_commerce.mapper.CustomerMapper;
import com.example.e_commerce.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerServiceImpl service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerServiceImpl service, CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDetailsDto> create(@Valid @RequestBody CustomerCreateDto dto) {
        var created = service.create(dto);
        return ResponseEntity.ok(mapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailsDto> get(@PathVariable Long id) {
        var customer = service.getOrThrow(id);
        return ResponseEntity.ok(mapper.toDto(customer));
    }
}
