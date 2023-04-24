package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
