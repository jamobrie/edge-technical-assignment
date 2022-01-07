package com.edge.numberChecker.numberChecker.assignment;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class NumberFinderImplTest {

    @Test
    void checkThatNumberExistsInFile_whenNumberExists_thenStringShouldConfirmItDoesExist() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        numberFinder.checkThatNumberExistsInFile();

        //assert String exists
        fail();
    }

    @Test
    void checkThatNumberExistsInFile_whenNumberDoesNotExist_thenStringShouldConfirmItDoesNot() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        numberFinder.checkThatNumberExistsInFile();

        //assert String not exists

        fail();
    }

    @Test
    void contains_whenNumberIsPresent_then() {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 100;

        numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        fail();
    }

    @Test
    void contains_whenNumberIsPresentAndMultipleDuplicatesExist_then() {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 12;

        numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        fail();
    }

    @Test
    void contains_whenNumberIsNotPresent_then() {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldNotExistInList = 77;

        numberFinder.contains(valueThatShouldNotExistInList, customNumberEntityList);

        fail();
    }

    @Test
    void readFromFile_whenValid_thenListOfCustomerEntityShouldBeReturned() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/main/resources/ListOfDummyValues.json");

        //assert list size, not null
        fail();
    }

    @Test
    void readFromFile_whenInvalidFilePathIsProvided_thenThrowException() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/main/resources/ListOfIncorrectFilepath123abcDummyValues.json");

        //assert global error handling is caught
        fail();
    }

    @Test
    void readFromFile_whenFileContainsNulls_thenAvoidNPE() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/main/resources/ListOfDummyValues.json");

        //assert that the presence of nulls does not affect the returned list adversely
        fail();
    }

    @Test
    void readFromFile_whenUnexpectedText_thenThrowException() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/main/resources/ListOfDummyValues.json");

        //assert exception is thrown when file contains bizarre format or patterns
        fail();
    }

    public List<CustomNumberEntity> customNumberTestData() {
        //TODO Read from test file to produce java list for testing
        return null;
    }


}