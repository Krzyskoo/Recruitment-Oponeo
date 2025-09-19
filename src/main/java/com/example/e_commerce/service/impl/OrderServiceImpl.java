package com.example.e_commerce.service.impl;

import com.example.e_commerce.component.TaxCalculator;
import com.example.e_commerce.dto.order.OrderCreateDto;
import com.example.e_commerce.dto.order.OrderDetailsDto;
import com.example.e_commerce.exception.*;
import com.example.e_commerce.mapper.OrderMapper;
import com.example.e_commerce.model.customer.Customer;
import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.order.OrderItem;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.repo.CustomerRepo;
import com.example.e_commerce.repo.OrderRepo;
import com.example.e_commerce.repo.ProductRepo;
import com.example.e_commerce.service.IOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;
    private final TaxCalculator tax;
    private final OrderMapper mapper;


    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo, ProductRepo productRepo, TaxCalculator tax, OrderMapper mapper) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.tax = tax;
        this.mapper = mapper;
    }

    @Override
    public Order create(OrderCreateDto dto) {
        Customer customer = customerRepo.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (dto.items().isEmpty()) throw new OrderEmptyException("Order cannot be empty");

        Order order = new Order();
        BigDecimal totalNet = BigDecimal.ZERO;
        BigDecimal totalGross = BigDecimal.ZERO;

        for (var it : dto.items()) {
            Product p = productRepo.findById(it.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + it.productId()));
            if (it.quantity() < 1) throw new QuantityException("Quantity must be >= 1");

            var unitNet = tax.scale(p.getNetPrice());
            var lineNet = tax.scale(unitNet.multiply(BigDecimal.valueOf(it.quantity())));
            var lineGross = tax.netToGross(lineNet, p.getVatRate());

            var oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(p);
            oi.setQuantity(it.quantity());
            oi.setUnitNet(unitNet);
            oi.setLineNet(lineNet);
            oi.setLineGross(lineGross);

            order.addItem(oi);
            totalNet = totalNet.add(lineNet);
            totalGross = totalGross.add(lineGross);
        }

        order.setCustomer(customer);
        order.setTotalNet(tax.scale(totalNet));
        order.setTotalGross(tax.scale(totalGross));

        return orderRepo.save(order);
    }

    @Override
    public Order getOrThrow(String id) {
        return orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

    }

    @Override
    public OrderDetailsDto getDto(String id) {
        var order = getOrThrow(id);
        return mapper.toDetailsDto(order);
    }
}
