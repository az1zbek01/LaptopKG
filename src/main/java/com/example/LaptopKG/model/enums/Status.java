package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {

    ACTIVE("активный"), DELETED("удаленный");
    private final String status;

}
