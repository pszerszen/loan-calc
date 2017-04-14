package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class Results {
    private boolean unemployed;
    private boolean ableToPayBills;
    private boolean ableToPayBillsWithPartner;
    private boolean ableToPayInstallments;
    private boolean ableToPayInstallmentsWithPartner;

    private boolean oldEnough;
    private boolean tooOld;
    private boolean partnerTooOld;
    private boolean willClientLiveLongEnough;
    private boolean willClientsPartnerLiveLongEnough;
}
