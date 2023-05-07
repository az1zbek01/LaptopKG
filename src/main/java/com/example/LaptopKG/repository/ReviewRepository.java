package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.Review;
import com.example.LaptopKG.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByLaptopIdAndUser(Long laptopId, User user);

    List<Review> findAllByLaptop(Laptop laptop);
}
