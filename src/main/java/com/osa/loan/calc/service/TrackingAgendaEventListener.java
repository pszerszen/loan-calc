package com.osa.loan.calc.service;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class TrackingAgendaEventListener extends DefaultAgendaEventListener {

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        Rule rule = event.getMatch().getRule();

        String ruleName = rule.getName();
        Map<String, Object> ruleMetaDataMap = rule.getMetaData();

        StringBuilder builder = new StringBuilder("Rule fired: " + ruleName);

        if (ruleMetaDataMap.size() > 0) {
            builder.append("\n  With [")
                    .append(ruleMetaDataMap.size())
                    .append("] meta-data:");
            ruleMetaDataMap.forEach((key, value) ->
                    builder.append("\n    key=").append(key)
                            .append(", value=").append(value));
        }

        log.debug(builder.toString());
    }

}
