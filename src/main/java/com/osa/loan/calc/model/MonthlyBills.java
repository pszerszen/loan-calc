package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class MonthlyBills {

    private Double estate;
    private Double living;
    private Double insurance;
    private Double otherLoans;
    private Double others;

    public Double getTotalPayments() {
        return estate + living + insurance + otherLoans + others;
    }
}
