package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Category {

    BASE("Базовые"), GAMING("Игровые"), ULTRABOOK("Ультрабуки");

    private final String category;

    public static Category of(String category) {
        return Stream.of(Category.values())
                .filter(p -> p.getCategory().equals(category))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
