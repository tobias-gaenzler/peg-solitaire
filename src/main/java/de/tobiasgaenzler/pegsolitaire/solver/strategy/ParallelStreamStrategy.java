package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.solver.SerializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This strategy will assemble all reachable positions (modulo symmetry).
 */
@Component
public class ParallelStreamStrategy implements WinningPositionsStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ParallelStreamStrategy.class);
    private final List<Path> positionFilePaths = new ArrayList<>();
    private final SerializationService serializationService;

    @Autowired
    public ParallelStreamStrategy(SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    @Override
    public List<Path> solve(Board board, Long startPosition) {
        positionFilePaths.clear();
        assembleReachablePositions(board, startPosition);
        long start = System.currentTimeMillis();
        removeNonWinningPositions(board);
        long totalSolutionTime = System.currentTimeMillis() - start;
        logger.info("Time non winning positions: {}\n", totalSolutionTime);
        return positionFilePaths;
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
        Path path = serializationService.storePositionsInFile(board, Set.of(startPosition), numberOfStartPins);
        positionFilePaths.add(path);

        long totalTime = 0L;
        for (int numberOfRemainingPieces = numberOfStartPins - 1; (numberOfRemainingPieces > 0); numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            Set<Long> previousPositions = serializationService.readPositionsFromFile(path);
            Set<Long> consecutivePositions = ConcurrentHashMap.newKeySet();
            previousPositions.stream().parallel().forEach(currentPosition -> {
                for (long consecutivePosition : board.getConsecutivePositions(currentPosition)) {
                    if (consecutivePositions.contains(consecutivePosition)) {
                        continue;
                    }
                    boolean inSet = false;
                    for (long symPos : board.getSymmetricPositions(consecutivePosition)) {
                        //check if a symmetric position is already in the set
                        if (consecutivePositions.contains(symPos)) {
                            inSet = true;
                            break;
                        }
                    }
                    //add position if no equivalent (symmetric) position is already in set
                    if (!inSet) {
                        consecutivePositions.add(consecutivePosition);
                    }
                }
            });
            path = serializationService.storePositionsInFile(board, consecutivePositions, numberOfRemainingPieces);
            positionFilePaths.add(path);

            totalTime += (System.currentTimeMillis() - start);
            logger.info("{}: {} reachable positions in {} ms", numberOfRemainingPieces, consecutivePositions.size(),
                    (System.currentTimeMillis() - start));
            removeSymmetricPositions(board, consecutivePositions);
        }
        logger.info("TOTAL TIME REACHABLE POSITIONS: {} ms", totalTime);
    }

    /**
     * Due to concurrency there might be symmetric entries in the sets of reachable positions.
     * Remove symmetric entries from reachable positions.
     * Removing the positions after calculation is faster than preventing that symmetric positions are added
     * (using synchronization).
     *
     * @param board Board where the positions live
     */
    private void removeSymmetricPositions(Board board, Set<Long> positions) {
        Set<Long> redundantPositions = ConcurrentHashMap.newKeySet();
        long start = System.currentTimeMillis();
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
        if (!redundantPositions.isEmpty()) {
            logger.info("Removed {} symmetric positions in {} ms", redundantPositions.size(), (System.currentTimeMillis() - start));
        }
    }

    /**
     * remove all positions which are not part of a solution.
     *
     * @param board the board, the positions live on
     */
    private void removeNonWinningPositions(Board board) {
        int numberOfPegs = positionFilePaths.size();
        // go backwards (files are ordered: 0: start position, ... ,numberOfPegs-1: positions with one peg)
        for (int pegs = numberOfPegs - 1; pegs > 0; pegs--) {
            Set<Long> positions = serializationService.readPositionsFromFile(positionFilePaths.get(pegs - 1));
            Set<Long> followingPositions = serializationService.readPositionsFromFile(positionFilePaths.get(pegs));
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
            removeSymmetricPositions(board, winningPositions);
            logger.info("{}: {} winning positions (all: {})", pegs, winningPositions.size(), positions.size());
            serializationService.storePositionsInFile(board, winningPositions, numberOfPegs - pegs + 1);
        }
    }
}