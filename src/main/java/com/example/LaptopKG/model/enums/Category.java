package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

    BASE("Базовые"), GAMING("Игровые"), ULTRA_BOOK("Ультрабуки");

    private final String category;


}
