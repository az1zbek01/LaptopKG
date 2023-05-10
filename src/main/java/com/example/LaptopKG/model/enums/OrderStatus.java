package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    NEW("Новый"), APPROVED("Подтвержденный"), CANCELED("Отмененный"), PAID("Оплаченный"), CLOSED("Закрытый");
    private final String orderStatus;

    public static OrderStatus of(String orderStatus) {
        return Stream.of(OrderStatus.values())
                .filter(p -> p.getOrderStatus().equals(orderStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
