package com.osa.loan.calc.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private LocalDate birthDay;
    private boolean married;
    private int children;
    private Double monthlySalary;
    private Loan loan;
    private MonthlyBills bills;
}
