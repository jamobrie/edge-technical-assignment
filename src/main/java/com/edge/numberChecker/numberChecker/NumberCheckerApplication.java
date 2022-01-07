package com.edge.numberChecker.numberChecker;

import com.edge.numberChecker.numberChecker.assignment.NumberFinderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class NumberCheckerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NumberCheckerApplication.class, args);

        NumberFinderImpl numberChecker = new NumberFinderImpl();
        numberChecker.checkThatNumberExistsInFile();

    }


}
