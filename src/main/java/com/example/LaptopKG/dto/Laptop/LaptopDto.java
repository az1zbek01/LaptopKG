package com.example.LaptopKG.dto.Laptop;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaptopDto {

    String model;
    String description;
    int price;
    int amount;

}
