package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

    WITH_CARD("Картой"), WITH_CASH("Наличными");
    private final String paymentType;

    public static PaymentType of(String paymentType) {
        return Stream.of(PaymentType.values())
                .filter(p -> p.getPaymentType().equals(paymentType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
