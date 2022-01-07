package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(null, noSuchFileException.getCause());
    }

    @Test
    void readFromFile_whenFileContainsNullsAndInvalidValues_thenIgnoreValuesInList() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> actualList = numberFinder.readFromFile("src/test/resources/ListOfNullAndInvalidNumberValues.json");

        assertEquals(0, actualList.size());
    }

    @Test
    void readFromFile_whenUnexpectedText_thenThrowException() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();

        UnrecognizedPropertyException unrecognizedPropertyException = assertThrows(UnrecognizedPropertyException.class, () ->
                numberFinder.readFromFile("src/test/resources/ListOfDummyValuesWithInvalidStructure.json"));

        //TODO Add global exception handling for more accurate error message
        assertThat(unrecognizedPropertyException.getMessage().contains(
                "Unrecognized field \"numberTest\" (class com.edge.numberChecker.numberChecker.assignment.CustomNumberEntity), not marked as ignorable (one known property: \"number\"])\n" +
                " at [Source: (String)\"[\n" +
                "  {\n" +
                "    \"number\": \"67\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"numberTest\": \"45\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"number\": null\n" +
                "  },\n" +
                "  {\n" +
                "    \"numberAbc\": \"45\"\n" +
                "  }\n" +
                "]\"; line: 6, column: 20] (through reference chain: java.util.ArrayList[1]->com.edge.numberChecker.numberChecker.assignment.CustomNumberEntity[\"numberTest\"])"
        ));

    }

    public List<CustomNumberEntity> customNumberTestData() {
        //TODO Read from test file to produce java list for testing
        return null;
    }


}