package com.edge.numberChecker.numberChecker;

import com.edge.numberChecker.numberChecker.assignment.NumberFinderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class NumberCheckerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NumberCheckerApplication.class, args);

        NumberFinderImpl numberChecker = new NumberFinderImpl();

        System.out.println("Enter a number to check!");
        Scanner scanner = new Scanner(System.in);

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
