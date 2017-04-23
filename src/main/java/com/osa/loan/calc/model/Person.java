package com.osa.loan.calc.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Person {

    private LocalDate birthDay;
    private Double monthlySalary;
    private Integer children;
    private List<LocalDate> childrenBirthDays;
    private Boolean married;
    private Boolean man;

    private LocalDate partnerBirthDay;
    private Double partnerMonthlySalary;

    private Loan loan;
    private MonthlyBills bills;

}
