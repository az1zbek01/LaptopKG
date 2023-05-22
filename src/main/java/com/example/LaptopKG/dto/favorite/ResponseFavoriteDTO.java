package com.example.LaptopKG.dto.favorite;

import com.example.LaptopKG.dto.laptop.ResponseLaptopDTO;
import com.example.LaptopKG.model.Favorite;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.laptop.ResponseLaptopDTO.toResponseLaptopDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseFavoriteDTO {
    Long id;
    ResponseLaptopDTO laptop;

    public static ResponseFavoriteDTO toGetFavoriteDto(Favorite favorite){
        return ResponseFavoriteDTO.builder()
                .id(favorite.getId())
                .laptop(toResponseLaptopDTO(favorite.getLaptop()))
                .build();
    }

    public static List<ResponseFavoriteDTO> toGetFavoriteDto(List<Favorite> favorites){
        return favorites.stream().map(ResponseFavoriteDTO::toGetFavoriteDto).collect(Collectors.toList());
    }
}
