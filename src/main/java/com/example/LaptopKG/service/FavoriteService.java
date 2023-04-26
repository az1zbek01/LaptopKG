package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.favorite.AddFavoriteDto;
import com.example.LaptopKG.dto.favorite.GetFavoriteDto;
import com.example.LaptopKG.exception.AlreadyExistException;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Favorite;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.FavoriteRepository;
import com.example.LaptopKG.repository.LaptopRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.favorite.GetFavoriteDto.toGetFavoriteDto;

@Service
@AllArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final LaptopRepository laptopRepository;

    // Get all favorites
    public List<GetFavoriteDto> getAllFavoritesOfUser(User user) {
        // Return favorites mapping from entity to dto
        return toGetFavoriteDto(
                // find all favorites from db
                favoriteRepository.findAll()
                        .stream()
                        // filter favorites by user
                        .filter(favorite -> favorite.getStatus() == Status.ACTIVE)
                        .filter(favorite -> favorite.getUser().equals(user))
                        .collect(Collectors.toList())
        );
    }

    // Get favorite by id
    public GetFavoriteDto getFavoriteById(Long id) {
        // Find active favorite in DB by id
        Favorite favorite = favoriteRepository.findById(id)
                .filter(f -> f.getStatus() == Status.ACTIVE)
                // throw exception if favorite doesn't exist
                .orElseThrow(
                        () -> new NotFoundException("Избранное с id " + id + " не найдено")
                );

        return toGetFavoriteDto(favorite);
    }

    // Favorite adding
    public GetFavoriteDto addFavorite(AddFavoriteDto addFavoriteDto, User user){
        // Check if exists favorite of the user with the current laptop
        if(favoriteRepository.existsByLaptopIdAndUser(addFavoriteDto.getLaptopId(), user)){
            Favorite favorite = favoriteRepository.findByLaptopIdAndUser(addFavoriteDto.getLaptopId(), user).get();
            favorite.setStatus(Status.ACTIVE);
            favoriteRepository.save(favorite);
            return toGetFavoriteDto(favorite);
        }

        Favorite favorite = Favorite.builder()
                // Check if laptop exists by id
                .laptop(laptopRepository.findById(addFavoriteDto.getLaptopId())
                        .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                        .orElseThrow(
                                () -> new LaptopNotFoundException("Ноутбук с id " + addFavoriteDto.getLaptopId() + " не найден")
                        )
                )
                .user(user)
                .status(Status.ACTIVE)
                .build();

        favoriteRepository.save(favorite);
        return toGetFavoriteDto(favorite);
    }

    // Favorite deleting
    public ResponseEntity<String> deleteFavoriteById(Long id) {
        // Find favorite by id and check if it is active
        Favorite favorite = favoriteRepository.findById(id)
                .filter(f -> f.getStatus() == Status.ACTIVE)
                // throw exception if laptop wasn't found or is not active
                .orElseThrow(
                        () -> new NotFoundException("Избранное с id " + id + " не найдено")
                );
        // Mark favorite as deleted and save it
        favorite.setStatus(Status.DELETED);
        favoriteRepository.save(favorite);

        // Return status 200 and message
        return ResponseEntity.ok("Favorite was successfully deleted");
    }

}
