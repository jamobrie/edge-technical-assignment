package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class NumberFinderImpl implements NumberFinder {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String checkThatNumberExistsInFile(int yourNumberToCheck) throws IOException {
        List<CustomNumberEntity> allExistingNumbers = readFromFile("src/main/resources/ListOfDummyValues.json");

        String result = contains(yourNumberToCheck, allExistingNumbers) ? " ... it does exist in the list. Success!" : " ... it does not exist in the list. Failure!";

        return "Based on the number you provided of " + yourNumberToCheck + result;
    }

    @Override
    public List<CustomNumberEntity> readFromFile(String filePath) throws IOException {
        log.info("Preparing to read from file");

        String fileToJson = new String(Files.readAllBytes(Paths.get(filePath)));

        List<CustomNumberEntity> customNumberEntityList = objectMapper.readValue(fileToJson, new TypeReference<>() {
        });

        List<CustomNumberEntity> validList = customNumberEntityList.stream().filter(this::onlyValidNumberEntities).collect(Collectors.toList());

        List<CustomNumberEntity> uniqueList = validList.stream().distinct().collect(Collectors.toList());

        return uniqueList;
    }

    //TODO List:
    //2. Do an initial manual test
    //3. Do the jUnit tests and clean it
    //4. Try to identify any other exceptions ... maybe add global exception handling

    private boolean onlyValidNumberEntities(CustomNumberEntity customNumberEntity) {
        if (customNumberEntity.getNumber() != null) {
            if (isNumeric(customNumberEntity.getNumber())) {
                return true;
            }
        }

        return false;
    }

    private boolean isNumeric(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean contains(int valueToFind, List<CustomNumberEntity> customNumberEntityList) {
        FastestComparator fastestComparator = new FastestComparator();

        log.info("Preparing to check if value of: " + valueToFind + " exists in the list of test data");
        Optional<CustomNumberEntity> optionalCustomNumberEntity = customNumberEntityList.stream()
                .filter(customer -> fastestComparator.compare(valueToFind, customer) == 0)
                .findFirst();

        return optionalCustomNumberEntity.isPresent();
    }

}
