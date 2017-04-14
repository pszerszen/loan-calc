package com.osa.loan.calc.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private LocalDate birthDay;
    private Double monthlySalary;
    private int children;
    private boolean married;

    private LocalDate partnerBirthDay;
    private Double partnerMonthlySalary;

    private Loan loan;
    private MonthlyBills bills;
}
