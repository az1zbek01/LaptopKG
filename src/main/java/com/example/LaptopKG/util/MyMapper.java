package com.example.LaptopKG.util;


import com.example.LaptopKG.dto.hardware.CreateHardwareDto;
import com.example.LaptopKG.dto.hardware.GetHardwareDto;
import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.HardwareType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MyMapper {

    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<HardwareType, String> hardwareTypeConverter =
                src -> src.getSource() == null ? null : src.getSource().getHardwareType();

        mapper.typeMap(Hardware.class, GetHardwareDto.class)
                .addMappings(m -> m.using(hardwareTypeConverter)
                        .map(Hardware::getHardwareType, GetHardwareDto::setHardwareType));

        Converter<String, HardwareType> createHardwareDtoTypeConverter =
                src -> src.getSource() == null ? null : HardwareType.of(src.getSource());

        mapper.typeMap(CreateHardwareDto.class, Hardware.class)
                .addMappings(m -> m.using(createHardwareDtoTypeConverter)
                        .map(CreateHardwareDto::getHardwareType, Hardware::setHardwareType));

        return mapper;
    }

}
