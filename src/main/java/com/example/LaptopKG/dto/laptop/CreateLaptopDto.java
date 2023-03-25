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
public class CreateLaptopDto {

    //todo: create mapper and replace entities with ids

    List<Hardware> model;
    String description;
    int price;
    int amount;
    int discount;
    Brand brand;
    Category category;

    public Laptop toLaptop() {
        return Laptop.builder()
                .model(this.getModel())
                .description(this.getDescription())
                .price(this.getPrice())
                .amount(this.getAmount())
                .brand(this.getBrand())
                .discount(this.getDiscount())
                .category(this.getCategory())
                .status(Status.ACTIVE)
                .build();
    }
}
