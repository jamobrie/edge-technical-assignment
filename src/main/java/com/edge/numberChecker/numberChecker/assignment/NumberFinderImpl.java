package com.edge.numberChecker.numberChecker.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class NumberFinderImpl implements NumberFinder {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public void checkThatNumberExistsInFile() {
        List<CustomNumberEntity> allNumbers = readFromFile("src/main/resources/ListOfDummyValues.json");

        Scanner in = new Scanner(System.in);
        int inputtedNumber = in.nextInt();

        log.info("Enter a number and we will check if it is present within the list");

        contains(inputtedNumber, allNumbers);

    }

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> customNumberEntityList) {
        //Emphasis placed on speed of process
        FastestComparator fastestComparator = new FastestComparator();

        log.info("Preparing to check if value of: " + valueToFind + " exists in the list of test data");
        customNumberEntityList.forEach(customer -> {
            fastestComparator.compare(valueToFind, customer);
        });

        //Perform calculation correctly before returning
        return false;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) {
        log.info("Preparing to read from file");

        //1. Read the file contents from "src/main/resources/ListOfDummyValues.json" where there are multiple CustomNumberEntity entries

        //2.Convert the file to a StringBuilder

        //3. Make safe the null values

        //4. Convert StringBuilder to CustomNumberEntity list

        //5. Return the response

        return null;
    }

}
