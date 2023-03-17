package com.example.LaptopKG.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Guarantee {

    NO_GUARANTEE(0), SIX_MONTH_GUARANTEE(6), ONE_YEAR_GUARANTEE(12);

    private final int guarantee;

}
