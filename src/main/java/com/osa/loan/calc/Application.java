package com.osa.loan.calc;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = Application.class)
@EnableConfigurationProperties
public class Application {

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public Type questionsType() {
        return new TypeToken<Map<String, String>>() {}.getType();
    }

    @Bean
    public List<String> droolsFiles() {
        return ImmutableList.of("payments");
    }

    @Bean
    public StatefulKnowledgeSession readKnowledgeSession(List<String> droolsFiles) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        droolsFiles.stream()
                .map(name -> name.concat(".drl"))
                .map(ResourceFactory::newClassPathResource)
                .forEach(resource -> kbuilder.add(resource, ResourceType.DRL));
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (!errors.isEmpty()) {
            errors.forEach(err -> log.error("drools error: \n" + err));
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase.newStatefulKnowledgeSession();
    }
}
