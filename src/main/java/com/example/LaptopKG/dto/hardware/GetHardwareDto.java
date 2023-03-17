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
public class GetHardwareDto {

    Long id;
    String name;
    HardwareType hardwareType;

    public GetHardwareDto hardwareToDto(Hardware hardware){
        return GetHardwareDto.builder()
                .id(hardware.getId())
                .hardwareType(hardware.getHardwareType())
                .name(hardware.getName())
                .build();
    }
    
}
