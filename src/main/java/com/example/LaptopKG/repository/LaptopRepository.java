package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    List<Laptop> findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String query, String query1);
}
