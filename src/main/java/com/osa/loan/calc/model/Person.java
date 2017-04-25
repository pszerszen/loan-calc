package com.osa.loan.calc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class Person {
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDay;
    private Double monthlySalary;
    private Integer children;
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private List<LocalDate> childrenBirthDays;
    private Boolean married;
    private Boolean man;

    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate partnerBirthDay;
    private Double partnerMonthlySalary;

    private Loan loan;
    private MonthlyBills bills;

}
