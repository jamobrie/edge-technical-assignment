package com.edge.numberChecker.numberChecker.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public final class FastestComparator {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Get an int and CustomNumberEntity values as input and compare them as a int numbers
     * Time needed to make the comparison will be between 5 and 10 seconds
     *
     * @param firstValue
     * @param secondValue
     * @return 0 if both values are equal,
     * > 0 if first value is greater
     * < 0 if second value is greater
     */
    public int compare(int firstValue, CustomNumberEntity secondValue) {
        log.info("Preparing to compare values of: " + firstValue + "and " + secondValue);

        Random random = new Random();
        int mSeconds = (random.nextInt(6) + 5) * 1000; //milliseconds
        int secondValueAsNumber = Integer.parseInt(secondValue.getNumber());
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
            //error while sleeping. Do nothing.
        }
        return firstValue - secondValueAsNumber;
    }
}
