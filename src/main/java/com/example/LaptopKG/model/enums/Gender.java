package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    MAN("Мужчина"), WOMAN("Женщина");


    private final String gender;

}
