package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.*;
import de.tobiasgaenzler.pegsolitaire.solver.SerializationService;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HighMemoryUsageStrategyTest {

    @Test
    public void testParallelStreamStrategyForQuadraticBoardSizeFour() {
        Board board = new QuadraticBoardSizeFour(new BitManipulator(), new PositionRenderer());
        SerializationService serializationService = new SerializationService();
        WinningPositionsStrategy strategy = new HighMemoryUsageStrategy(serializationService);
        long startPosition = 0B1110_1011_1111_1111L;
        List<Path> winningPositionsPaths = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions =
                winningPositionsPaths.stream().map(path -> {
                    Set<Long> positions = serializationService.readPositionsFromTxtFile(path);
                    return positions.size();
                }).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        assertThat(numberOfWinningPositions).isEqualTo(List.of(
                1, 4, 10, 26, 60, 97, 117, 99, 61, 29, 11, 3, 1, 1));
    }

    @Test
    public void testParallelStreamStrategyForQuadraticBoardSizeFive() {
        Board board = new QuadraticBoardSizeFive(new BitManipulator(), new PositionRenderer());
        SerializationService serializationService = new SerializationService();
        WinningPositionsStrategy strategy = new HighMemoryUsageStrategy(serializationService);
        long startPosition = 0B11111_11111_11111_11011_11111L;
        List<Path> winningPositionsPaths = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions =
                winningPositionsPaths.stream().map(path -> {
                    Set<Long> positions = serializationService.readPositionsFromTxtFile(path);
                    return positions.size();
                }).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        assertThat(numberOfWinningPositions).isEqualTo(List.of(1, 2, 6, 23, 95, 327, 966, 2422, 5094, 8939, 13015, 15558, 15193, 12194, 8205, 4701, 2318, 973, 336, 101, 26, 8, 3, 2));
    }

    // this test takes a while (approximately 2 minutes on my computer).
    @Test
    public void testParallelStreamStrategyForEnglishBoard() {
        Board board = new EnglishBoard(new BitManipulator(), new PositionRenderer());
        SerializationService serializationService = new SerializationService();
        WinningPositionsStrategy strategy = new HighMemoryUsageStrategy(serializationService);
        Long startPosition = board.getStartPosition();
        List<Path> winningPositionsPaths = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions =
                winningPositionsPaths.stream().map(path -> {
                    Set<Long> positions = serializationService.readPositionsFromTxtFile(path);
                    return positions.size();
                }).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        // compare http://www.gibell.net/pegsolitaire/English/index.html
        assertThat(numberOfWinningPositions).isEqualTo(List.of(
                1,
                1,
                2,
                8,
                38,
                164,
                635,
                2089,
                6174,
                16020,
                35749,
                68326,
                112788,
                162319,
                204992,
                230230,
                230230,
                204992,
                162319,
                112788,
                68326,
                35749,
                16020,
                6174,
                2089,
                635,
                164,
                38,
                8,
                2,
                1,
                2));
    }
}
