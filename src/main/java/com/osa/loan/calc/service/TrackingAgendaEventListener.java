package com.osa.loan.calc.service;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.runtime.rule.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TrackingAgendaEventListener extends DefaultAgendaEventListener {

    private List<Match> matchList = new ArrayList<>();

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();

        String ruleName = rule.getName();
        Map<String, Object> ruleMetaDataMap = rule.getMetaData();

        matchList.add(event.getMatch());
        StringBuilder sb = new StringBuilder("Rule fired: " + ruleName);

        if (ruleMetaDataMap.size() > 0) {
            sb.append("\n  With [")
                    .append(ruleMetaDataMap.size())
                    .append("] meta-data:");
            for (String key : ruleMetaDataMap.keySet()) {
                sb.append("\n    key=")
                        .append(key)
                        .append(", value=")
                        .append(ruleMetaDataMap.get(key));
            }
        }

        log.debug(sb.toString());
    }

    public boolean isRuleFired(String ruleName) {
        for (Match a : matchList) {
            if (a.getRule().getName().equals(ruleName)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        matchList.clear();
    }

    public final List<Match> getMatchList() {
        return matchList;
    }

    public String matchsToString() {
        if (matchList.size() == 0) {
            return "No matchs occurred.";
        } else {
            StringBuilder sb = new StringBuilder("Matchs: ");
            for (Match match : matchList) {
                sb.append("\n  rule: ")
                        .append(match.getRule().getName());
            }
            return sb.toString();
        }
    }

}
