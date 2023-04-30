package com.example.LaptopKG.dto.hardware;




import com.example.LaptopKG.model.Hardware;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseHardwareDTO {
    Long id;
    String name;
    String hardwareType;

    public static ResponseHardwareDTO toGetHardwareDto(Hardware hardware){
        return ResponseHardwareDTO.builder()
                .id(hardware.getId())
                .name(hardware.getName())
                .hardwareType(hardware.getHardwareType().getHardwareType())
                .build();
    }

    public static List<ResponseHardwareDTO> toGetHardwareDto(List<Hardware> hardwareList){
        return hardwareList.stream().map(ResponseHardwareDTO::toGetHardwareDto).collect(Collectors.toList());
    }
}
