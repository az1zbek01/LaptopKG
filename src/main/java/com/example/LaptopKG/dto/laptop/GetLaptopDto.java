package com.example.LaptopKG.dto.laptop;

import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.Laptop;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLaptopDto {


    Long id;
    String description;
    int price;
    int amount;
    int discount;
    String brand;
    String category;
    int guarantee;

    public static GetLaptopDto toGetLaptopDto(Laptop laptop){
        return GetLaptopDto.builder()
                .description(laptop.getDescription())
                .price(laptop.getPrice())
                .amount(laptop.getAmount())
                .discount(laptop.getDiscount())
                .brand(laptop.getBrand().getBrand())
                .category(laptop.getCategory().getCategory())
                .guarantee(laptop.getGuarantee().getGuarantee())
                .build();
    }

    public static List<GetLaptopDto> toGetLaptopDto(List<Laptop> laptops){
        return laptops.stream().map(GetLaptopDto::toGetLaptopDto).collect(Collectors.toList());
    }

}
