package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.board.QuadraticBoardSizeFive;
import de.tobiasgaenzler.pegsolitaire.board.QuadraticBoardSizeFour;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelStreamStrategyTest {

    @Test
    public void testParallelStreamStrategyForQuadraticBoardSizeFour() {
        Board board = new QuadraticBoardSizeFour(new BitManipulator());
        WinningPositionsStrategy strategy = new ParallelStreamStrategy();
        long startPosition = 0B1110_1011_1111_1111L;
        List<Set<Long>> winningPositions = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions = winningPositions.stream().map(Set::size).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        assertThat(numberOfWinningPositions).isEqualTo(List.of(
                0,
                1,
                1,
                3,
                11,
                29,
                61,
                99,
                117,
                97,
                60,
                26,
                10,
                4,
                1,
                0));
    }

    @Test
    public void testParallelStreamStrategyForQuadraticBoardSizeFive() {
        Board board = new QuadraticBoardSizeFive(new BitManipulator());
        WinningPositionsStrategy strategy = new ParallelStreamStrategy();
        long startPosition = 0B11111_11111_11111_11011_11111L;
        List<Set<Long>> winningPositions = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions = winningPositions.stream().map(Set::size).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        assertThat(numberOfWinningPositions).isEqualTo(List.of(
                0,
                2,
                3,
                8,
                26,
                101,
                336,
                973,
                2318,
                4701,
                8205,
                12194,
                15193,
                15558,
                13015,
                8939,
                5094,
                2422,
                966,
                327,
                95,
                23,
                6,
                2,
                1,
                0));
    }

    // this test takes a while (approximately 2 minutes on my computer).
    @Test
    public void testParallelStreamStrategyForEnglishBoard() {
        Board board = new EnglishBoard(new BitManipulator());
        WinningPositionsStrategy strategy = new ParallelStreamStrategy();
        Long startPosition = board.getStartPosition();
        List<Set<Long>> winningPositions = strategy.solve(board, startPosition);
        List<Integer> numberOfWinningPositions = winningPositions.stream().map(Set::size).collect(Collectors.toList());
        // only test the number of winning positions (assume that if the numbers are correct than the position itself are correct as well)
        // compare http://www.gibell.net/pegsolitaire/English/index.html
        assertThat(numberOfWinningPositions).isEqualTo(List.of(
                0,
                2,
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
                1,
                0));
    }
}
