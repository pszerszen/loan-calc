package com.osa.loan.calc;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class Questions {

    private static final String QUESTIONS_FILE = "questions.json";

    private Map<String, String> questions;

    @Autowired
    public Questions(Gson gson, Type type) throws URISyntaxException, IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(getClass().getResource(QUESTIONS_FILE).toURI()));
        questions = gson.fromJson(reader, type);
    }

    public String getQuestion(String key) {
        return questions.get(key);
    }
}
