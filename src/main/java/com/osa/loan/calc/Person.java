package com.osa.loan.calc;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private LocalDate birthDay;
    private boolean married;
    private int children;
    private Loan loan;
    private MonthlyBills bills;
}
