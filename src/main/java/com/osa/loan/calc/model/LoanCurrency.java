package com.osa.loan.calc.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum LoanCurrency {
    PLN(1D),
    EUR(4.2226),
    USD(3.8675),
    GBP(4.985),
    CHF(3.888),
    CZK(0.0157),
    SEK(0.438),
    CNY(0.561),
    RUB((0.068));

    @Getter
    private final double plnProportion;

    public LoanCurrency getValue() {
        return this;
    }
}
