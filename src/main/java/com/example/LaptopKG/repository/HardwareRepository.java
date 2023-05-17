package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HardwareRepository extends JpaRepository<Hardware, Long> {

    Optional<Hardware> findByName(String hardware);
}
