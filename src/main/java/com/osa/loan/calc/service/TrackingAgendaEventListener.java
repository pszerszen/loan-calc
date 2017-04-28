package com.osa.loan.calc.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Component
@Scope(SCOPE_PROTOTYPE)
public class TrackingAgendaEventListener extends DefaultAgendaEventListener {

    @Getter
    private final List<String> logList = new ArrayList<>();

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

        String logMsg = builder.toString();
        logList.add(logMsg);
        log.debug(logMsg);
    }

}
