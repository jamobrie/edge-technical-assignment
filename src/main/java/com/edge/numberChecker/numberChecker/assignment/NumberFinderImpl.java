package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


public class NumberFinderImpl implements NumberFinder {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final AtomicInteger doesNumberExistCounter = new AtomicInteger();

    public CheckerResult checkThatNumberExistsInFile(int yourNumberToCheck) throws IOException {
        Instant startTime = Instant.now();

        List<CustomNumberEntity> allExistingNumbers = readFromFile("src/main/resources/ListOfDummyValues.json");
        boolean wasNumberFound = contains(yourNumberToCheck, allExistingNumbers);
        String resultOfChecking = wasNumberFound ? "yes it was found!" : "no it was not found!";

        CheckerResult checkerResult = new CheckerResult(yourNumberToCheck, resultOfChecking, startTime);

        log.info(checkerResult.toString());

        return checkerResult;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) throws IOException {
        log.info("Preparing to read from file");

        String fileToJson = new String(Files.readAllBytes(Paths.get(filePath)));

        List<CustomNumberEntity> customNumberEntityList = objectMapper.readValue(fileToJson, new TypeReference<>() {
        });

        List<CustomNumberEntity> validList = customNumberEntityList.stream().filter(this::onlyValidNumbersToBeCollected).collect(Collectors.toList());

        return validList.stream().distinct().collect(Collectors.toList());
    }

    private boolean onlyValidNumbersToBeCollected(CustomNumberEntity customNumberEntity) {
        if (customNumberEntity.getNumber() != null) {
            return canBeParsedAsInteger(customNumberEntity.getNumber());
        }

        return false;
    }

    private boolean canBeParsedAsInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> customNumberEntityList) {
        log.info("Preparing to check if value of: " + valueToFind + " exists in the list of test data");
        //parallelStream
        FastestComparator fastestComparator = new FastestComparator();

        boolean isNumberFound = false;
        while (!isNumberFound) {

            for (CustomNumberEntity ce : customNumberEntityList) {
//                Task taskRunner = new Task(valueToFind, ce);
//                taskRunner.start();

                fastestComparator.compare(valueToFind, ce);
                //taskRunner.getDoesNumberExistCounter().get() > 0
                if (fastestComparator.compare(valueToFind, ce) == 0) {
                    System.out.println("sucessess, number found!!!!!!");
                    isNumberFound = true;
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " " + valueToFind + " " + ce.getNumber());
            }
            break;
        }
        return isNumberFound;
    }
}
