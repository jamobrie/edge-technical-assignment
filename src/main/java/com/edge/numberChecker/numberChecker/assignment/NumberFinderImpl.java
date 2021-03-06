package com.edge.numberChecker.numberChecker.assignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class NumberFinderImpl implements NumberFinder {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FindNumberResult checkThatNumberExistsInFile(int yourNumberToCheck) throws IOException {
        Instant startTime = Instant.now();

        List<CustomNumberEntity> allExistingNumbers = readFromFile("src/main/resources/ListOfDummyValues.json");

        boolean wasNumberFound = false;
        try {
            wasNumberFound = contains(yourNumberToCheck, allExistingNumbers);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            log.error("Error occurred during Future execution whereby the polled Callables failed to get processed");
        }

        String resultOfChecking = wasNumberFound ? "yes it was found!" : "no it was not found!";

        FindNumberResult findNumberResult = new FindNumberResult(yourNumberToCheck, resultOfChecking, startTime);

        log.info(findNumberResult.toString());

        return findNumberResult;
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
    public boolean contains(int valueToFind, List<CustomNumberEntity> customNumberEntityList) throws ExecutionException, InterruptedException {
        log.info("Preparing to check if value of: " + valueToFind + " exists in the list of test data");

        List<FastestComparator> simpleListSleepingCallables = new ArrayList<>();
        for (CustomNumberEntity customNumberEntity : customNumberEntityList) {
            FastestComparator sleepingCallable = new FastestComparator(valueToFind, customNumberEntity);
            simpleListSleepingCallables.add(sleepingCallable);
        }

        final ExecutorService pool = Executors.newFixedThreadPool(customNumberEntityList.size());
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(pool);

        for (final Callable<Integer> callable : simpleListSleepingCallables) {
            service.submit(callable);
        }
        pool.shutdown();
        boolean wasFound = false;
        while (true) {
            Future<Integer> future;
            if (pool.isTerminated()) {
                future = service.poll();
                if (future == null) {
                    log.info("Number cannot be found in file!");
                    break;
                }
            } else {
                future = service.take();
            }
            log.info("The result for this instance of FastestComparator.compare(): " + future.get());
            if (future.get().equals(0)) {
                log.info("The number has been found by thread: " + Thread.currentThread().getName());
                pool.shutdown();
                wasFound = true;
                return wasFound;
            }
        }

        return wasFound;
    }

}
