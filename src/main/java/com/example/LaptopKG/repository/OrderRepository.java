package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
