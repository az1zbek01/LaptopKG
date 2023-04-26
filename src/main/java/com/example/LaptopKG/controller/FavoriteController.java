package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.favorite.AddFavoriteDto;
import com.example.LaptopKG.dto.favorite.GetFavoriteDto;
import com.example.LaptopKG.dto.laptop.CreateLaptopDto;
import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
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
    public List<GetFavoriteDto> getAllFavoritesOfUser(@AuthenticationPrincipal User user){
        return favoriteService.getAllFavoritesOfUser(user);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение избранного по айди"
    )
    public GetFavoriteDto getFavoriteById(@PathVariable long id){
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление в избранное"
    )
    public GetFavoriteDto addFavorite(@RequestBody AddFavoriteDto addFavoriteDto,
                                      @AuthenticationPrincipal User user){
        return favoriteService.addFavorite(addFavoriteDto, user);
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
