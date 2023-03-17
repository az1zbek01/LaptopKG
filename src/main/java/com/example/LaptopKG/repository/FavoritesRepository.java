package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
}
