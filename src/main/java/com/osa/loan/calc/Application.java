package com.osa.loan.calc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.Map;

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
}
