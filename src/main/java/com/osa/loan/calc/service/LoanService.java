package com.osa.loan.calc.service;

import com.osa.loan.calc.model.Person;
import com.osa.loan.calc.model.Results;
import com.osa.loan.calc.model.TotalPayments;
import com.osa.loan.calc.model.Verdict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private final Utils utils;
    private final KnowledgeBase knowledgeBase;
    private final ApplicationContext context;

    public synchronized Verdict checkCreditworthiness(Person person) {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        try {
            TrackingAgendaEventListener eventListener = getEventListener();

            session.addEventListener(eventListener);
            session.insert(person);
            session.insert(new Results());

            session.setGlobal("utils", utils);
            session.setGlobal("totalPayments", new TotalPayments());
            session.setGlobal("verdict", new Verdict());

            log.debug("Running rules");
            session.fireAllRules();

            Verdict verdict = (Verdict) session.getGlobal("verdict");
            verdict.setTotalPayments(((TotalPayments) session.getGlobal("totalPayments")).getValue());
            verdict.setRulesLog(eventListener.getLogList());
            return verdict;
        } finally {
            log.debug("Drools finished.");
            session.destroy();
            log.debug("Session destroyed.");
        }
    }

    private TrackingAgendaEventListener getEventListener() {
        return context.getBean(TrackingAgendaEventListener.class);
    }
}
