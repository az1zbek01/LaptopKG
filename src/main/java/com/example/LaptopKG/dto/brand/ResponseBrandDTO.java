package com.example.LaptopKG.dto.brand;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseBrandDTO {

    Long id;
    String name;



}
