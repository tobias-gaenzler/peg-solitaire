package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.solver.SerializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This strategy will assemble all winning positions (modulo symmetry).
 * All sets of positions are kept in memory which leads to high memory consumption.
 * A winning position is a position from which the end position can be reached i.e. which is part of a solution.
 */
@Component
public class HighMemoryUsageStrategy implements WinningPositionsStrategy {

    private static final Logger logger = LoggerFactory.getLogger(HighMemoryUsageStrategy.class);
    public static final String NAME = "winningPositionsHighMem";
    private final List<Path> txtFilePaths = new ArrayList<>();
    private final SerializationService serializationService;
    private final List<Set<Long>> reachablePositions = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    public HighMemoryUsageStrategy(SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    @Override
    public List<Path> solve(Board board, Long startPosition) {
        txtFilePaths.clear();
        reachablePositions.clear();
        assembleReachablePositions(board, startPosition);
        Instant start = Instant.now();
        removeNonWinningPositions(board);
        logger.info("Time non winning positions: {}\n", Duration.between(start, Instant.now()).toMillis());
        for (int pegs = 0; pegs < reachablePositions.size(); pegs++) {
            if (!reachablePositions.get(pegs).isEmpty()) {
                Path txtFilePath = serializationService.storePositionsInTxtFile(board,
                        reachablePositions.get(reachablePositions.size() - pegs - 1),
                        pegs);
                txtFilePaths.add(txtFilePath);
            }
        }
        return txtFilePaths;
    }

    @Override
    public String getName() {
        return NAME;
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
            reachablePositions.add(ConcurrentHashMap.newKeySet());
        }

        // add startPosition to reachablePositions
        reachablePositions.get(numberOfStartPins).add(startPosition);

        long totalTime = 0L;
        for (int numberOfRemainingPieces = numberOfStartPins - 1; (numberOfRemainingPieces > 0); numberOfRemainingPieces--) {
            long start = System.currentTimeMillis();
            Set<Long> followingPositions = reachablePositions.get(numberOfRemainingPieces);
            reachablePositions.get(numberOfRemainingPieces + 1).stream().parallel().forEach(currentPosition -> {
                for (long consecutivePosition : board.getConsecutivePositions(currentPosition)) {
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
                        followingPositions.add(consecutivePosition);
                    }
                }
            });
            totalTime += (System.currentTimeMillis() - start);
            logger.info("{}: {} reachable positions in {} ms", numberOfRemainingPieces, reachablePositions.get(numberOfRemainingPieces).size(),
                    (System.currentTimeMillis() - start));
            removeSymmetricPositions(board, followingPositions);
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
        Instant start = Instant.now();
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
            logger.info("Removed {} symmetric positions in {} ms", redundantPositions.size(), Duration.between(start, Instant.now()).toMillis());
        }
    }

    /**
     * remove all positions which are not part of a solution.
     *
     * @param board the board, the positions live on
     */
    private void removeNonWinningPositions(Board board) {
        for (int pegs = 2; pegs < reachablePositions.size(); pegs++) {
            Instant start = Instant.now();
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
            logger.info("{}: {} winning positions in {} ms (all: {})", pegs, winningPositions.size(), (Duration.between(start, Instant.now()).toMillis()), positions.size());
            reachablePositions.set(pegs, winningPositions);
        }
    }
}