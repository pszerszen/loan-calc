package com.osa.loan.calc.service;

import com.osa.loan.calc.model.Loan;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import static java.lang.Math.pow;
import static java.time.LocalDate.now;

@Component
public class Utils {

    private static final int MONTHS_IN_YEAR = Month.values().length;
    private static final int INSTALLMENT_COUNT_PER_CAPITALISATION = MONTHS_IN_YEAR;

    @Getter
    @Value("${average.age.man}")
    private Double averageManAge;

    @Getter
    @Value("${average.age.woman}")
    private Double averageWomanAge;

    /**
     * Wzór na obliczenie raty stałej kredytu:
     * <p>
     * rata = S * q^n * (q-1)/(q^n-1)
     * <p>
     * S – kwota zaciągniętego kredytu
     * n – ilość rat
     * q – współczynnik równy 1 + (r / m), gdzie
     * q^n – „q” do potęgi „n”
     * r – oprocentowanie kredytu
     * m – ilość rat w okresie dla którego obowiązuje oprocentowanie „r”. Najczęściej oprocentowanie podawanej jest w skali roku, a raty płacone są
     * co miesiąc, więc „m” wtedy jest równe 12.
     *
     * @param loan object containing loan data
     * @return monthly installment
     */
    public double countMonthlyLoanInstallment(Loan loan) {
        double q = 1 + loan.getPercentage() / INSTALLMENT_COUNT_PER_CAPITALISATION;

        return loan.getPrice() * pow(q, loan.getPeriod()) * (q - 1) / pow(q, loan.getPeriod() - 1);
    }

    public double getAgeInYears(LocalDate birthDay) {
        Period lifePeriod = Period.between(birthDay, now());

        return lifePeriod.getMonths() / MONTHS_IN_YEAR;
    }

    public boolean childWillBeAbleToCarryOn(LocalDate birthday, boolean isMan, LocalDate childBirthday) {
        LocalDate predictedDeathDate = getPredictedDeathDate(birthday, isMan);

        return Period.between(childBirthday, predictedDeathDate).getYears() >= 18;
    }

    public int getNumberOfInstallmentsThatCouldBePaid(LocalDate birthDay, boolean isMan) {
        return Period.between(now(), getPredictedDeathDate(birthDay, isMan))
                .getMonths();
    }

    private LocalDate getPredictedDeathDate(final LocalDate birthDay, final boolean isMan) {
        int currentAgeInMonths = getAgeInMonths(birthDay);
        int averageAge = averageAgeInMonths(isMan);

        return birthDay.plusMonths(averageAge - currentAgeInMonths);
    }

    private int getAgeInMonths(LocalDate birthDay) {
        return Period.between(birthDay, now()).getMonths();
    }

    private int averageAgeInMonths(boolean isMan) {
        return BigDecimal.valueOf(isMan ? averageManAge : averageWomanAge)
                .multiply(BigDecimal.valueOf(MONTHS_IN_YEAR))
                .intValue();
    }
}
