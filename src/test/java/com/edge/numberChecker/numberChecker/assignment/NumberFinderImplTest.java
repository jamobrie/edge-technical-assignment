package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NumberFinderImplTest {

    @RepeatedTest(value = 50)
    void checkThatNumberExistsInFile_whenNumberExists_thenStringShouldConfirmItDoesExist() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        FindNumberResult findNumberResult = numberFinder.checkThatNumberExistsInFile(12);

        assertEquals(12, findNumberResult.getYourNumberToCheck());
        assertEquals("yes it was found!", findNumberResult.getResultOfChecking());
        assertTrue(findNumberResult.getTimeRequiredToCheckInMilliseconds() > 5000);
        assertTrue(findNumberResult.getTimeRequiredToCheckInMilliseconds() < 10500);
    }

    @RepeatedTest(value = 50)
    void checkThatNumberExistsInFile_whenNumberDoesNotExist_thenStringShouldConfirmItDoesNot() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        FindNumberResult findNumberResult = numberFinder.checkThatNumberExistsInFile(62);

        assertEquals(62, findNumberResult.getYourNumberToCheck());
        assertEquals("no it was not found!", findNumberResult.getResultOfChecking());
        assertTrue(findNumberResult.getTimeRequiredToCheckInMilliseconds() > 1);
        assertTrue(findNumberResult.getTimeRequiredToCheckInMilliseconds() < 10500);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void checkThatNumberExistsInFile_whenUsingDifferentData_thenValueShouldBeFoundInCertainScenarios_toEnsureThreadSafety(FindNumberResult expectedFindNumberResult) throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        FindNumberResult actualFindNumberResult = numberFinder.checkThatNumberExistsInFile(expectedFindNumberResult.getYourNumberToCheck());

        assertEquals(expectedFindNumberResult.getYourNumberToCheck(), actualFindNumberResult.getYourNumberToCheck());
        assertEquals(expectedFindNumberResult.getResultOfChecking(), actualFindNumberResult.getResultOfChecking());
        assertTrue(actualFindNumberResult.getTimeRequiredToCheckInMilliseconds() > 1);
        assertTrue(actualFindNumberResult.getTimeRequiredToCheckInMilliseconds() < 10500);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(new FindNumberResult(62, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(67, "yes it was found!", Instant.now())),
                Arguments.of(new FindNumberResult(62, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(45, "yes it was found!", Instant.now())),
                Arguments.of(new FindNumberResult(-3, "yes it was found!", Instant.now())),
                Arguments.of(new FindNumberResult(12, "yes it was found!", Instant.now())),
                Arguments.of(new FindNumberResult(88, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(77, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(102, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(100, "yes it was found!", Instant.now())),
                Arguments.of(new FindNumberResult(101, "no it was not found!", Instant.now())),
                Arguments.of(new FindNumberResult(3, "yes it was found!", Instant.now()))
        );
    }

    @Test
    void contains_whenNumberIsPresent_thenAssertTrue() throws IOException, ExecutionException, InterruptedException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 100;

        boolean doesValueExist = numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        assertTrue(doesValueExist);
    }

    @Test
    void contains_whenNumberIsPresentAndMultipleDuplicatesExist_thenAssertTrue() throws IOException, ExecutionException, InterruptedException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        List<CustomNumberEntity> customNumberEntityList = customNumberTestData();

        int valueThatShouldExistInList = 12;

        boolean doesValueExist = numberFinder.contains(valueThatShouldExistInList, customNumberEntityList);

        assertTrue(doesValueExist);
    }

    @Test
    void contains_whenNumberIsNotPresent_thenAssertFalse() throws IOException, ExecutionException, InterruptedException {
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

        assertTrue(unrecognizedPropertyException.getMessage().contains("Unrecognized field \"numberTest\" (class com.edge.numberChecker.numberChecker.assignment.CustomNumberEntity)"));
    }

    public List<CustomNumberEntity> customNumberTestData() throws IOException {
        NumberFinderImpl numberFinder = new NumberFinderImpl();
        return numberFinder.readFromFile("src/test/resources/ListOfDummyValues.json");
    }


}