package com.edge.numberChecker.numberChecker.assignment;

import java.util.List;

public class NumberFinderImpl implements NumberFinder {

    public void checkForNumber(){
        readFromFile("dummyValue");
    }

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
        return false;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) {
        return null;
    }

}
