package com.edge.numberChecker.numberChecker;

import com.edge.numberChecker.numberChecker.assignment.NumberFinderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class NumberCheckerApplication {

    private static final Logger log = LoggerFactory.getLogger(NumberCheckerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NumberCheckerApplication.class, args);

        NumberFinderImpl numberChecker = new NumberFinderImpl();

        log.info("Enter a number to check! Note, only numbers are accepted as input");
        Scanner scanner = new Scanner(System.in);

        boolean keepApplicationAlive = true;
        while (keepApplicationAlive) {
            while (!scanner.hasNextInt()) scanner.next();
            {
                int enteredNumber = scanner.nextInt();
                if (enteredNumber != 0) {
                    numberChecker.checkThatNumberExistsInFile(enteredNumber);
                } else {
                    scanner.close();
                    keepApplicationAlive = false;
                }
            }
        }

        System.exit(0);

    }


}
