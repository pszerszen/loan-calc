package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class Loan {

    private Double price;
    private Integer period;
    private String currency;
    private Double percentage;

    private double monthlyLoanInstallment;
}
