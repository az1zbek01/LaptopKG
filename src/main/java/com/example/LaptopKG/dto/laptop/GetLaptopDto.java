package com.example.LaptopKG.dto.laptop;

import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.Laptop;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLaptopDto {


    Long id;
    List<Hardware> model;
    String description;
    int price;
    int amount;
    int discount;
    Brand brand;
    Category category;

    public GetLaptopDto laptopToDto(Laptop laptop) {
        return GetLaptopDto.builder()
                .id(laptop.getId())
                .model(laptop.getModel())
                .description(laptop.getDescription())
                .price(laptop.getPrice())
                .discount(laptop.getDiscount())
                .amount(laptop.getAmount())
                .brand(laptop.getBrand())
                .category(laptop.getCategory())
                .build();
    }

}
