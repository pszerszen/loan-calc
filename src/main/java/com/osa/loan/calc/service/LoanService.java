package com.osa.loan.calc.service;

import com.osa.loan.calc.model.Person;
import com.osa.loan.calc.model.Results;
import com.osa.loan.calc.model.Verdict;
import lombok.RequiredArgsConstructor;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private final Utils utils;
    private final KnowledgeBase knowledgeBase;

    public synchronized Verdict checkCreditworthiness(Person person) {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        try {

            session.insert(person);
            session.insert(new Results());

            session.setGlobal("utils", utils);
            session.setGlobal("totalPayments", BigDecimal.valueOf(0));
            session.setGlobal("verdict", new Verdict());

            session.fireAllRules();

            return (Verdict) session.getGlobal("verdict");
        } finally {
            session.destroy();
        }
    }
}
