package com.example.LaptopKG.repository;

import com.example.LaptopKG.dto.order.ResponseOrderDTO;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.Order;
import com.example.LaptopKG.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);


}
