package com.edge.numberChecker.numberChecker;

import com.edge.numberChecker.numberChecker.assignment.NumberFinderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class NumberCheckerApplication {

    private static final Logger log = LoggerFactory.getLogger(NumberCheckerApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NumberCheckerApplication.class, args);

        NumberFinderImpl numberChecker = new NumberFinderImpl();

        System.out.println("Enter a number to check!");
        Scanner scanner = new Scanner(System.in);
//TODO Make the scanner only accept integers
        boolean keepApplicationAlive = true;
        while (keepApplicationAlive) {
            int enteredNumber = scanner.nextInt();
            if (enteredNumber != 0) {
                numberChecker.checkThatNumberExistsInFile(enteredNumber);
            } else {
                scanner.close();
                keepApplicationAlive = false;
            }
        }

        System.exit(0);

    }


}
