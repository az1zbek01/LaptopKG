package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

    WITH_CARD("Картой"), WITH_CASH("Наличкой");
    private final String paymentType;

}
