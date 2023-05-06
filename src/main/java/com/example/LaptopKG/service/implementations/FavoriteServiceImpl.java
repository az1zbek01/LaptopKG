package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.favorite.RequestFavoriteDTO;
import com.example.LaptopKG.dto.favorite.ResponseFavoriteDTO;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Favorite;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.FavoriteRepository;
import com.example.LaptopKG.repository.LaptopRepository;
import com.example.LaptopKG.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.favorite.ResponseFavoriteDTO.toGetFavoriteDto;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final LaptopRepository laptopRepository;

    public List<ResponseFavoriteDTO> getAllFavoritesOfUser(User user) {
        return toGetFavoriteDto(
                favoriteRepository.findAll()
                        .stream()
                        .filter(favorite -> favorite.getStatus() == Status.ACTIVE)
                        .filter(favorite -> favorite.getUser().equals(user))
                        .collect(Collectors.toList())
        );
    }

    public ResponseFavoriteDTO getFavoriteById(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .filter(f -> f.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Избранное с id " + id + " не найдено")
                );

        return toGetFavoriteDto(favorite);
    }

    public ResponseFavoriteDTO addFavorite(RequestFavoriteDTO requestFavoriteDTO, User user){
        if(favoriteRepository.existsByLaptopIdAndUser(requestFavoriteDTO.getLaptopId(), user)){
            Favorite favorite = favoriteRepository.findByLaptopIdAndUser(requestFavoriteDTO.getLaptopId(), user).get();
            favorite.setStatus(Status.ACTIVE);
            favoriteRepository.save(favorite);
            return toGetFavoriteDto(favorite);
        }

        Favorite favorite = Favorite.builder()
                .laptop(laptopRepository.findById(requestFavoriteDTO.getLaptopId())
                        .filter(laptop -> laptop.getStatus() == Status.ACTIVE)
                        .orElseThrow(
                                () -> new LaptopNotFoundException("Ноутбук с id " + requestFavoriteDTO.getLaptopId() + " не найден")
                        )
                )
                .user(user)
                .status(Status.ACTIVE)
                .build();

        favoriteRepository.save(favorite);
        return toGetFavoriteDto(favorite);
    }

    public ResponseEntity<String> deleteFavoriteById(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .filter(f -> f.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Избранное с id " + id + " не найдено")
                );

        favorite.setStatus(Status.DELETED);
        favoriteRepository.save(favorite);

        return ResponseEntity.ok("Избранное успешно удалено");
    }

}
