package com.example.LaptopKG.dto.hardware;

import com.example.LaptopKG.model.enums.HardwareType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HardwareNameDto {

    String name;


    HardwareType hardwareType;

}
