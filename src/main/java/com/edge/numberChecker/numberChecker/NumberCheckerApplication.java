package com.edge.numberChecker.numberChecker;

import com.edge.numberChecker.numberChecker.assignment.NumberFinderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumberCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberCheckerApplication.class, args);

        NumberFinderImpl numberChecker = new NumberFinderImpl();
        numberChecker.checkForNumber();
    }

}
