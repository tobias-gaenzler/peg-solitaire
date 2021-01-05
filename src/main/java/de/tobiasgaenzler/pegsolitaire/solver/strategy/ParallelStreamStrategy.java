package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This strategy will assemble all reachable positions (modulo symmetry).
 */
@Component
public class ParallelStreamStrategy implements WinningPositionsStrategy {
    private static final Logger logger = LoggerFactory.getLogger(ParallelStreamStrategy.class);


    private final List<Set<Long>> reachablePositions = new ArrayList<>();

    @Override
    public List<Set<Long>> solve(Board board, Long startPosition) {
        reachablePositions.clear();
        assembleReachablePositions(board, startPosition);
        long start = System.currentTimeMillis();
        check(board);
        logger.info("Time check: {}", (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        removeNonWinningPositions(board);
        long totalSolutionTime = System.currentTimeMillis() - start;
        logger.info("Time non winning positions: {}\n", totalSolutionTime);
        storePositionsInFile(board);
        return reachablePositions;
    }

    private void storePositionsInFile(Board board) {
        List<String> strings = reachablePositions.stream().skip(1)// skip first because it is always null
                .filter(list -> !list.isEmpty())// ignore empty lists
                .map(positions ->//
                        positions.stream()//
                                .filter(Objects::nonNull)// ignore null values
                                .map(String::valueOf)//
                                .collect(Collectors.joining(",")))//
                .collect(Collectors.toList());
        Iterator<String> reversedStream = new LinkedList<>(strings).descendingIterator();

        List<String> reverseOrderStrings = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(reversedStream,
                        Spliterator.ORDERED), false).collect(
                Collectors.toList());
        Path path = Paths.get(board.getName() + "_positions.txt");
        try {
            Files.write(path, reverseOrderStrings, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Due to concurrency there might be symmetric entries in the sets of reachable positions.
     * Remove symmetric entries from reachable positions.
     *
     * @param board Board where the positions live
     */
    private void check(Board board) {
        for (int pins = 0; pins < reachablePositions.size(); pins++) {
            Set<Long> redundantPositions = ConcurrentHashMap.newKeySet();
            long start = System.currentTimeMillis();
            Set<Long> positions = reachablePositions.get(pins);
            positions.stream().parallel().forEach(position -> {
                for (long symmetricPosition : board.getSymmetricPositions(position)) {
                    if (symmetricPosition != position && positions.contains(symmetricPosition)) {
                        redundantPositions.add(position);
                        break;
                    }
                }
            });
            for (Long redundantPosition : redundantPositions) {
                for (long symmetricPosition : board.getSymmetricPositions(redundantPosition)) {
                    if (symmetricPosition != redundantPosition && positions.contains(symmetricPosition)) {
                        positions.remove(redundantPosition);
                        break;
                    }
                }
            }
            logger.info("Check {}: {}, Time: {}", pins, redundantPositions.size(), (System.currentTimeMillis() - start));
        }
    }

    /**
     * remove all positions which are not part of a solution.
     *
     * @param board the board, the positions live on
     */
    private void removeNonWinningPositions(Board board) {
        for (int pegs = 2; pegs < reachablePositions.size(); pegs++) {
            Set<Long> positions = reachablePositions.get(pegs);
            Set<Long> followingPositions = reachablePositions.get(pegs - 1);
            Set<Long> winningPositions = ConcurrentHashMap.newKeySet();
            positions.stream().parallel().forEach(position -> {
                for (long consecutivePosition : board.getConsecutivePositions(position)) {
                    for (long symmetricPosition : board.getSymmetricPositions(consecutivePosition)) {
                        if (followingPositions.contains(symmetricPosition)) {
                            winningPositions.add(position);
                            break;
                        }
                    }
                }
            });
            logger.info("{}: {} winning positions (all: {})", pegs, winningPositions.size(), positions.size());
            reachablePositions.set(pegs, winningPositions);
        }
    }

    /**
     * Assemble all reachable positions starting with startPosition.
     * Use the stream().parallel() for concurrency.
     *
     * @param board         the board where the positions live
     * @param startPosition start finding positions with this start position
     */
    private void assembleReachablePositions(Board board, Long startPosition) {
        int numberOfStartPins = board.getNumberOfPegs(startPosition);

        for (int i = 0; i <= numberOfStartPins + 1; i++) {
            reachablePositions.add(new HashSet<>());
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPins).add(startPosition);

        long totalTime = 0L;
        for (int numberOfRemainingPieces = numberOfStartPins - 1; (numberOfRemainingPieces > 0); numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            Set<Long> followingPositions = reachablePositions.get(numberOfRemainingPieces);
            reachablePositions.get(numberOfRemainingPieces + 1).stream().parallel().forEach(currentPosition -> {
                for (long consecutivePosition : board.getConsecutivePositions(currentPosition)) {
                    // synchronizing on the _contains_ operations is too slow
                    if (followingPositions.contains(consecutivePosition)) {
                        continue;
                    }
                    boolean inSet = false;
                    for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                        //check if a symmetric position is already in the set
                        if (followingPositions.contains(symPos)) {
                            inSet = true;
                            break;
                        }
                    }
                    //add position if no equivalent (symmetric) position is already in set
                    if (!inSet) {
                        // synchronizing on inserts is a good idea
                        synchronized (followingPositions) {
                            followingPositions.add(consecutivePosition);
                        }
                    }
                }
            });
            totalTime += (System.currentTimeMillis() - start);
            logger.info("{}: {} positions in {} ms", numberOfRemainingPieces, reachablePositions.get(numberOfRemainingPieces).size(),
                    (System.currentTimeMillis() - start));
        }
        logger.info("TOTAL TIME REACHABLE POSITIONS: {} ms", totalTime);
    }
}