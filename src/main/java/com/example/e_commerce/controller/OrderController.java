package com.example.e_commerce.controller;

import com.example.e_commerce.dto.order.OrderCreateDto;
import com.example.e_commerce.dto.order.OrderDetailsDto;
import com.example.e_commerce.mapper.OrderMapper;
import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderServiceImpl service;
    private final OrderMapper mapper;

    public OrderController(OrderServiceImpl service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDetailsDto> create(@Valid @RequestBody OrderCreateDto dto) {
        Order o = service.create(dto);
        return ResponseEntity.ok(mapper.toDetailsDto(o));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDetailsDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getDto(id));
    }
}
