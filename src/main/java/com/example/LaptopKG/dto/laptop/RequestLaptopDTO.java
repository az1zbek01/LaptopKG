package com.example.LaptopKG.dto.laptop;


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
public class RequestLaptopDTO {
    String name;
    String description;
    int price;
    int amount;
    Long brandId;
    String category;
    int guarantee;
    List<Long> hardwareIds;
}
