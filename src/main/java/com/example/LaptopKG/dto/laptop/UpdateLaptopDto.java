package com.example.LaptopKG.dto.laptop;

import com.example.LaptopKG.model.Brand;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLaptopDto {
    String description;
    int price;
    int amount;
    int discount;
    Long brandId;
    String category;
    int guarantee;
    String status;
    List<Long> hardwareIds;

}
