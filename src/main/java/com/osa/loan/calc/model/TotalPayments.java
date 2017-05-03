package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class TotalPayments {
    private double value = 0D;

    public void addPayment(double newPayment) {
        value += newPayment;
    }
}
