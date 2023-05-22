package com.example.LaptopKG.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Guarantee {

    NO_GUARANTEE(0), SIX_MONTH_GUARANTEE(6), ONE_YEAR_GUARANTEE(12);

    private final int guarantee;

    public static Guarantee of(int guarantee) {
        return Stream.of(Guarantee.values())
                .filter(p -> p.getGuarantee() == guarantee)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
