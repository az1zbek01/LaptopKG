package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByToken(String token);

    RefreshToken findByToken(String token);

    boolean existsByUserId(Long id);

    RefreshToken findByUserId(Long id);
}
