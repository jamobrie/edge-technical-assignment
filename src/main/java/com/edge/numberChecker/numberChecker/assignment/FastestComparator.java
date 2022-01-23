package com.edge.numberChecker.numberChecker.assignment;

import java.util.Random;
import java.util.concurrent.Callable;

//Not to be modified as mentioned in requirements
public final class FastestComparator implements Callable<Integer> {

    private final int valueToFind;
    private final CustomNumberEntity customNumberEntity;

    public FastestComparator(int valueToFind, CustomNumberEntity customNumberEntity) {
        this.valueToFind = valueToFind;
        this.customNumberEntity = customNumberEntity;
    }

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
        Random random = new Random();
        int mSeconds = (random.nextInt(6) + 5) * 1000; //milliseconds
        System.out.println(Thread.currentThread().getName() + " is sleeping for: " + mSeconds);
        int secondValueAsNumber = Integer.parseInt(secondValue.getNumber());
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
            //error while sleeping. Do nothing.
        }
        return firstValue - secondValueAsNumber;
    }

    @Override
    public Integer call() {
        return compare(this.valueToFind, this.customNumberEntity);
    }

}

