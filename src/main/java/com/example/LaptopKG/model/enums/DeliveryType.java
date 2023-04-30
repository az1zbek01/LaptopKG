package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum DeliveryType {

    WITH_DELIVERY("С доставкой"), SELF_CARRY("Самовывоз");
    private final String deliveryType;

    public static DeliveryType of(String deliveryType) {
        return Stream.of(DeliveryType.values())
                .filter(d -> d.getDeliveryType().equals(deliveryType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
