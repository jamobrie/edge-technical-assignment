package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberFinderImplTest {

    @Test
    void checkThatNumberExistsInFile_whenNumberExists_thenStringShouldConfirmItDoesExist() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        CheckerResult checkerResult = numberFinder.checkThatNumberExistsInFile(12);

        assertEquals(12, checkerResult.getYourNumberToCheck());
        assertEquals("yes it was found!", checkerResult.getResultOfChecking());
        assertTrue(checkerResult.getTimeRequiredToCheckInMilliseconds() > 1);
        assertTrue(checkerResult.getTimeRequiredToCheckInMilliseconds() < 5000);
    }

    @Test
    void checkThatNumberExistsInFile_whenNumberDoesNotExist_thenStringShouldConfirmItDoesNot() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        CheckerResult checkerResult = numberFinder.checkThatNumberExistsInFile(62);

        assertEquals(62, checkerResult.getYourNumberToCheck());
        assertEquals("no it was not found!", checkerResult.getResultOfChecking());
        assertTrue(checkerResult.getTimeRequiredToCheckInMilliseconds() > 1);
        assertTrue(checkerResult.getTimeRequiredToCheckInMilliseconds() < 5000);
    }

    @Test
    void contains_whenNumberIsPresent_thenAssertTrue() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 100;

        boolean doesValueExist = numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        assertTrue(doesValueExist);
    }

    @Test
    void contains_whenNumberIsPresentAndMultipleDuplicatesExist_thenAssertTrue() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 12;

        boolean doesValueExist = numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        assertTrue(doesValueExist);
    }

    @Test
    void contains_whenNumberIsNotPresent_thenAssertFalse() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldNotExistInList = 77;

        boolean doesValueExist = numberFinder.contains(valueThatShouldNotExistInList, customNumberEntityList);

        assertFalse(doesValueExist);
    }

    @Test
    void readFromFile_whenValid_thenListOfCustomerEntityShouldBeReturned_AndDuplicatesAreNotTakenIntoAccount() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/test/resources/ListOfDummyValues.json");

        assertEquals(6, actualList.size());
        assertEquals("67", actualList.get(0).getNumber());
        assertEquals("45", actualList.get(1).getNumber());
        assertEquals("-3", actualList.get(2).getNumber());
        assertEquals("12", actualList.get(3).getNumber());
        assertEquals("100", actualList.get(4).getNumber());
        assertEquals("3", actualList.get(5).getNumber());
    }

    @Test
    void readFromFile_whenInvalidFilePathIsProvided_thenThrowException() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();

        NoSuchFileException noSuchFileException = assertThrows(NoSuchFileException.class, () ->
                numberFinder.readFromFile("src/test/resources/ListOfIncorrectFilepath123abcDummyValues.json"));

        //TODO Add global exception handling for more accurate error message
        assertNull(noSuchFileException.getCause());
    }

    @Test
    void readFromFile_whenFileContainsNullsAndInvalidValues_thenIgnoreValuesInList() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/test/resources/ListOfNullAndInvalidNumberValues.json");

        assertEquals(0, actualList.size());
    }

    @Test
    void readFromFile_whenUnexpectedText_thenThrowException() {
        NumberFinderImpl numberFinder = new NumberFinderImpl();

        UnrecognizedPropertyException unrecognizedPropertyException = assertThrows(UnrecognizedPropertyException.class, () ->
                numberFinder.readFromFile("src/test/resources/ListOfDummyValuesWithInvalidStructure.json"));

        //TODO Add global exception handling for more accurate error message
        assertTrue(unrecognizedPropertyException.getMessage().contains("Unrecognized field \"numberTest\" (class com.edge.numberChecker.numberChecker.assignment.CustomNumberEntity)"));

    }

    public List<CustomNumberEntity> customNumberTestData() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        return numberFinder.readFromFile("src/test/resources/ListOfDummyValues.json");
    }


}