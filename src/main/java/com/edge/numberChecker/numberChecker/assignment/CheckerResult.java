package com.edge.numberChecker.numberChecker.assignment;

import java.time.Duration;
import java.time.Instant;

public class CheckerResult {

    int yourNumberToCheck;
    String resultOfChecking;
    long timeRequiredToCheckInMilliseconds;

    public CheckerResult(int yourNumberToCheck, String resultOfChecking, Instant startTime
    ) {
        Instant endTime = Instant.now();

        this.yourNumberToCheck = yourNumberToCheck;
        this.resultOfChecking = resultOfChecking;
        this.timeRequiredToCheckInMilliseconds = Duration.between(startTime, endTime).toMillis();
    }

    public int getYourNumberToCheck() {
        return yourNumberToCheck;
    }

    public String getResultOfChecking() {
        return resultOfChecking;
    }

    public long getTimeRequiredToCheckInMilliseconds() {
        return timeRequiredToCheckInMilliseconds;
    }

    @Override
    public String toString() {
        return "CheckerResult{" +
                "yourNumberToCheck=" + yourNumberToCheck +
                ", resultOfChecking='" + resultOfChecking + '\'' +
                ", timeRequiredToCheckInMilliseconds=" + timeRequiredToCheckInMilliseconds +
                '}';
    }

}
