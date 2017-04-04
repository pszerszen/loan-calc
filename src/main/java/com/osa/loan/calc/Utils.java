package com.osa.loan.calc;

import com.osa.loan.calc.model.Loan;
import org.springframework.stereotype.Component;

import static java.lang.Math.pow;

@Component
public class Utils {

    private static final int INSTALLMENT_COUNT_PER_CAPITALISATION = 12;

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
}
