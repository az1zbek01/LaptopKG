package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Favorite;
import com.example.LaptopKG.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByLaptopIdAndUser(Long laptopId, User user);
    Optional<Favorite> findByLaptopIdAndUser(Long laptopId, User user);
}
