package com.edge.numberChecker.numberChecker.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NumberFinderImpl implements NumberFinder {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public void checkForNumber(){
        readFromFile("dummyValue");
    }

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        return false;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) {
        log.info("Preparing to read from file");

        return null;
    }

}
