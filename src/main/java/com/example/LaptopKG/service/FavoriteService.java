package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.favorite.RequestFavoriteDTO;
import com.example.LaptopKG.dto.favorite.ResponseFavoriteDTO;
import com.example.LaptopKG.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoriteService {
    List<ResponseFavoriteDTO> getAllFavoritesOfUser(User user);
    ResponseFavoriteDTO getFavoriteById(Long id);
    ResponseFavoriteDTO addFavorite(RequestFavoriteDTO requestFavoriteDTO, User user);
    ResponseEntity<String> deleteFavoriteById(Long id);
}
