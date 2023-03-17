package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryType {

    WITH_DELIVERY("С доставкой"), SELF_CARRY("Самовывоз");
    private final String deliveryType;

}
