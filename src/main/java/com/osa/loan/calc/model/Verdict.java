package com.osa.loan.calc.model;

import lombok.Data;

@Data
public class Verdict {
    private int critical = 0;
    private int warnings = 0;
    private int positives = 0;

    public Verdict addCritical() {
        critical++;
        return this;
    }

    public Verdict addWarning() {
        warnings++;
        return this;
    }

    public Verdict addPositive() {
        positives++;
        return this;
    }
}
