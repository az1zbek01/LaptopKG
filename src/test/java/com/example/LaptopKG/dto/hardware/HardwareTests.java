package com.example.LaptopKG.dto.hardware;


import com.example.LaptopKG.model.Hardware;
import com.example.LaptopKG.model.enums.HardwareType;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

public class HardwareTests {


    private final ModelMapper mapper = new ModelMapper();

    @Test
    void mapperTest() {


//        TypeMap<CreateHardwareDto, Hardware> propertyMapper = mapper.createTypeMap(CreateHardwareDto.class, Hardware.class);
//        propertyMapper.addMappings(
//                mapper -> mapper.map(src -> HardwareType.of(src.getHardwareType()), Hardware::setHardwareType)
//        );

        Converter<String, HardwareType> hardwareTypeConverter =
                src -> src.getSource() == null ? null : HardwareType.of(src.getSource());

        mapper.typeMap(RequestHardwareDTO.class, Hardware.class)
                .addMappings(mapper -> mapper.using(hardwareTypeConverter)
                        .map(RequestHardwareDTO::getHardwareType, Hardware::setHardwareType));

        RequestHardwareDTO requestHardwareDTO = RequestHardwareDTO.builder()
                .name("test name")
                .hardwareType(HardwareType.RESOLUTION.getHardwareType())
                .build();



        Hardware hardware = mapper.map(requestHardwareDTO, Hardware.class);


        assertEquals(hardware.getHardwareType().getHardwareType(), requestHardwareDTO.getHardwareType());


    }

    @Test
    void EnumValueTest() {


        String val = HardwareType.RESOLUTION.getHardwareType();
        String val1 = "разрешение";

        HardwareType hardwareType = HardwareType.RESOLUTION;
        HardwareType hardwareType1 = HardwareType.of(val);


        assertEquals(val1, val);
        assertEquals(hardwareType1, hardwareType);

    }
}
