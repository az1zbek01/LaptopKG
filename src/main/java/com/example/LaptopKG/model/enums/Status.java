package com.example.LaptopKG.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Status {

    ACTIVE("активный"), NOT_ACTIVATED("неактивированный"), DELETED("удаленный");
    private final String status;


    public static Status of(String status) {
        return Stream.of(Status.values())
                .filter(p -> p.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
