package com.osa.loan.calc.service;

import com.osa.loan.calc.model.Loan;
import com.osa.loan.calc.model.MonthlyBills;
import com.osa.loan.calc.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonPreprocessor {

    private final Utils utils;

    public void preprocessPerson(Person person) {
        preprocess(person);
        Loan loan = person.getLoan();
        loan.setPrice(loan.getPrice() * loan.getCurrency().getPlnProportion());
        loan.setMonthlyLoanInstallment(utils.countMonthlyLoanInstallment(loan));
    }

    @SneakyThrows
    private <T> void preprocess(T object) {
        Arrays.stream(object.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (Integer.class.equals(type)) {
                preprocessInteger(field, object);
            } else if (Double.class.equals(type)) {
                preprocessDouble(field, object);
            } else if (Loan.class.equals(type) || MonthlyBills.class.equals(type)) {
                preprocessNested(field, object);
            } else if (Boolean.class.equals(type) || type.isEnum()) {
                assertObjectNotNutt(field, object);
            }
        });
    }

    @SneakyThrows
    private <T> void preprocessDouble(Field field, T object) {
        preprocessNumber(field, object, 0D);
    }

    @SneakyThrows
    private <T> void preprocessInteger(Field field, T object) {
        preprocessNumber(field, object, 0);
    }

    private <T, N extends Number> void preprocessNumber(final Field field, final T object, N defaultValue) throws IllegalAccessException {
        if (field.get(object) == null) {
            field.set(object, defaultValue);
        }
    }

    @SneakyThrows
    private <T> void preprocessNested(Field field, T object) {
        preprocess(field.get(object));
    }

    @SneakyThrows
    private <T> void assertObjectNotNutt(Field field, T object) {
        if (field.get(object) == null) {
            throw new IllegalArgumentException(String.format("A field %s in object of type %s is null.", field.getName(), object.getClass()));
        }
    }
}
