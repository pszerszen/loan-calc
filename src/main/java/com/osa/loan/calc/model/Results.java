package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class Results {
    private boolean unemployed;
    private boolean ableToPayBills;
    private boolean ableToPayBillsWithPartner;
    private boolean ableToPayInstallments;
    private boolean ableToPayInstallmentsWithPartner;
}
