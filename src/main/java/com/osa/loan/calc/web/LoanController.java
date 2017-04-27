package com.osa.loan.calc.web;

import com.google.common.collect.ImmutableList;
import com.osa.loan.calc.model.LoanCurrency;
import com.osa.loan.calc.model.Person;
import com.osa.loan.calc.model.Verdict;
import com.osa.loan.calc.service.LoanService;
import com.osa.loan.calc.service.PersonPreprocessor;
import com.osa.loan.calc.service.Questions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.tuple.ImmutablePair.of;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanController {

    private final Questions questions;
    private final PersonPreprocessor preprocessor;
    private final LoanService loanService;

    @ModelAttribute("civilStates")
    public List<Pair<String, Boolean>> getCivilStates() {
        return ImmutableList.of(
                of("Kawaler/Panna", false),
                of("Żonaty/Zamężna", true),
                of("W separacji", false),
                of("Wdowiec/Wdowa", false)
        );
    }

    @ModelAttribute("currencies")
    public List<LoanCurrency> getCurrencies() {
        return Arrays.stream(LoanCurrency.values()).collect(toList());
    }

    @RequestMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("welcome", "person", new Person());
    }

    @ResponseBody
    @RequestMapping(path = "/question", produces = TEXT_PLAIN_VALUE, method = GET)
    public String getQuestion(@RequestParam String key) {
        return questions.getQuestion(key);
    }

    @ResponseBody
    @RequestMapping(path = "/count", method = POST)
    public Verdict checkCreditworthiness(@ModelAttribute Person person) {
        preprocessor.preprocessPerson(person);
        Verdict verdict = loanService.checkCreditworthiness(person);
        log.debug("Verdict: {}", verdict);
        return verdict;
    }
}
