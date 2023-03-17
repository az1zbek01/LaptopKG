package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    NEW("Новый"), APPROVED("Подтвержденный"), CANCELED("Отмененный"), PAID("Оплаченный"), CLOSED("Закрытый");
    private final String orderStatus;

}
