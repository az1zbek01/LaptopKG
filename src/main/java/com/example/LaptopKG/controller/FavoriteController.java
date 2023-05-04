package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.favorite.RequestFavoriteDTO;
import com.example.LaptopKG.dto.favorite.ResponseFavoriteDTO;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с избранными ноутбуками",
        description = "В этом контроллеры есть возможности добавления и получения избранных ноутбуков"
)
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех избранных ноутбуков пользователя"
    )
    public List<ResponseFavoriteDTO> getAllFavoritesOfUser(@AuthenticationPrincipal User user){
        return favoriteService.getAllFavoritesOfUser(user);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение избранного по айди"
    )
    public ResponseFavoriteDTO getFavoriteById(@PathVariable long id){
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление в избранное"
    )
    public ResponseFavoriteDTO addFavorite(@RequestBody RequestFavoriteDTO requestFavoriteDTO,
                                           @AuthenticationPrincipal User user){
        return favoriteService.addFavorite(requestFavoriteDTO, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление из избранного"
    )
    public ResponseEntity<String> deleteFavoriteById(@PathVariable Long id){
        return favoriteService.deleteFavoriteById(id);
    }

}
