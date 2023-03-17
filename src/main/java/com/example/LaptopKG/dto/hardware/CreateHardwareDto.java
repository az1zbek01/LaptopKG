package com.example.LaptopKG.dto.hardware;

import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.HardwareType;
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
    HardwareType hardwareType;


    public Hardware toHardware() {
        return Hardware.builder()
                .name(this.getName())
                .hardwareType(this.getHardwareType())
                .build();
    }

}
