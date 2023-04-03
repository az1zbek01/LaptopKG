package com.example.LaptopKG.dto.hardware;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateHardwareDto {

    String name;

    String hardwareType;


}
