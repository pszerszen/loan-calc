package com.osa.loan.calc;

import lombok.Data;

@Data
public class MonthlyBills {

    private long estate;
    private long living;
    private long insurance;
    private long otherLoans;
    private long others;
}
