package com.example.LaptopKG.dto.brand;


import com.example.LaptopKG.model.Brand;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBrandDto {

    String brand;

    public Brand toBrand() {
        return Brand.builder()
                .brand(this.getBrand())
                .build();
    }
}
