package com.example.LaptopKG.dto.laptop;

import com.example.LaptopKG.dto.hardware.GetHardwareDto;
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
    List<GetHardwareDto> model;
    String description;
    int price;
    int amount;
    int discount;
    String brand;
    String category;
    int guarantee;

}
