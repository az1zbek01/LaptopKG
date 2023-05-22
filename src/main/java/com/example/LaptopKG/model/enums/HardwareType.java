package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum HardwareType{
    PROCESSOR("процессор"),
    DIAGONAL("диагональ"),
    OPERATING_SYSTEM("операционная система"),
    RAM_AMOUNT("объем оперативной памяти"),
    HARD_DRIVE_TYPE("тип жесткого диска"),
    STORAGE_CAPACITY("объем накопителя"),
    PROCESSOR_FREQUENCY("базовая частота процессора"),
    CORE_AMOUNT("количество ядер"),
    GRAPHICS_CARD("видеокарта"),
    RESOLUTION("разрешение");

    private final String hardwareType;

    public static HardwareType of(String hardwareType) {
        return Stream.of(HardwareType.values())
                .filter(p -> p.getHardwareType().equals(hardwareType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
