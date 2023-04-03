package com.example.LaptopKG.dto.brand;


import com.example.LaptopKG.dto.laptop.GetLaptopDto;
import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.Laptop;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetBrandDto {

    Long id;
    String brand;



}
