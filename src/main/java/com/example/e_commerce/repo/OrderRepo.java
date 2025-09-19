package com.example.e_commerce.repo;

import com.example.e_commerce.model.order.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, String> {
    @EntityGraph(attributePaths = {"customer", "items", "items.product"})
    Optional<Order> findById(String id);
}
