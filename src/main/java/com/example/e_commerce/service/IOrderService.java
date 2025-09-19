package com.example.e_commerce.service;

import com.example.e_commerce.dto.order.OrderCreateDto;
import com.example.e_commerce.dto.order.OrderDetailsDto;
import com.example.e_commerce.model.order.Order;

public interface IOrderService {
    Order create(OrderCreateDto dto);
    Order getOrThrow(String id);
    OrderDetailsDto getDto(String id);
}
