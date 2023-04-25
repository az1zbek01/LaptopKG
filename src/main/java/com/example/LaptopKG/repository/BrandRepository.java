package com.example.LaptopKG.repository;


import com.example.LaptopKG.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String brand);
}
