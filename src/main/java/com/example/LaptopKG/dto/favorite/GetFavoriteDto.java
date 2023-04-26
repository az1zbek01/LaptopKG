package com.example.LaptopKG.dto.favorite;

import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.model.Favorite;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.laptop.GetLaptopDto.toGetLaptopDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetFavoriteDto {
    Long id;
    GetLaptopDto laptop;

    public static GetFavoriteDto toGetFavoriteDto(Favorite favorite){
        return GetFavoriteDto.builder()
                .id(favorite.getId())
                .laptop(toGetLaptopDto(favorite.getLaptop()))
                .build();
    }

    public static List<GetFavoriteDto> toGetFavoriteDto(List<Favorite> favorites){
        return favorites.stream().map(GetFavoriteDto::toGetFavoriteDto).collect(Collectors.toList());
    }
}
