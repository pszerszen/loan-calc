package com.osa.loan.calc.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Data
public class Person {

    private LocalDate birthDay;
    private Double monthlySalary;
    private int children;
    private boolean married;
    private boolean man;

    private LocalDate partnerBirthDay;
    private Double partnerMonthlySalary;

    private Loan loan;
    private MonthlyBills bills;

    public void setMan(final String man) {
        this.man = StringUtils.equalsAnyIgnoreCase(man, "man", "M", "mężczyzna");
    }

    public void setMan(final boolean man) {
        this.man = man;
    }
}
