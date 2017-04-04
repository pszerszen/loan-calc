package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class Loan {

    private long price;
    private int period;
    private String currency;
    private double percentage;
}
