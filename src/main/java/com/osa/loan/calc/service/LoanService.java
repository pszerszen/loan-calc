package com.osa.loan.calc.service;

import com.osa.loan.calc.model.Loan;
import com.osa.loan.calc.model.Person;
import com.osa.loan.calc.model.Results;
import com.osa.loan.calc.model.Verdict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private final Utils utils;
    private final KnowledgeBase knowledgeBase;

    public synchronized Verdict checkCreditworthiness(Person person) {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        try {

            Loan loan = person.getLoan();
            loan.setMonthlyLoanInstallment(utils.countMonthlyLoanInstallment(loan));
            session.insert(person);
            session.insert(new Results());

            session.setGlobal("log", log);
            session.setGlobal("utils", utils);
            session.setGlobal("totalPayments", 0d);
            session.setGlobal("verdict", new Verdict());

            log.debug("Running rules");
            session.fireAllRules();

            return (Verdict) session.getGlobal("verdict");
        } finally {
            log.debug("Drools finished.");
            session.destroy();
            log.debug("Session destroyed.");
        }
    }
}
