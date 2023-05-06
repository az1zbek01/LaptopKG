package com.example.LaptopKG.dto.laptop;

import com.example.LaptopKG.dto.hardware.ResponseHardwareDTO;
import com.example.LaptopKG.model.Laptop;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.hardware.ResponseHardwareDTO.toGetHardwareDto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseLaptopDTO {
    Long id;
    String name;
    String description;
    int price;
    int amount;
    String brand;
    String category;
    int guarantee;
    List<ResponseHardwareDTO> hardwareList;

    Double averageScore;

    public static ResponseLaptopDTO toResponseLaptopDTO(Laptop laptop){
        return ResponseLaptopDTO.builder()
                .id(laptop.getId())
                .description(laptop.getDescription())
                .price(laptop.getPrice())
                .amount(laptop.getAmount())
                .name(laptop.getName())
                .brand(laptop.getBrand().getName())
                .category(laptop.getCategory().getCategory())
                .guarantee(laptop.getGuarantee().getGuarantee())
                .hardwareList(toGetHardwareDto(laptop.getHardwareList()))
                .averageScore(laptop.getAverageScore())
                .build();
    }

    public static List<ResponseLaptopDTO> toResponseLaptopDTO(List<Laptop> laptops){
        return laptops.stream().map(ResponseLaptopDTO::toResponseLaptopDTO).collect(Collectors.toList());
    }

}
