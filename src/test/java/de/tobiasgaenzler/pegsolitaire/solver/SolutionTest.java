package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SolutionTest {

    @Test
    public void testSolutionInstantiation() {
        Solution solution = new Solution(32);
        assertThat(solution.getPositions().size()).isEqualTo(32);
        for (Long position : solution.getPositions()) {
            assertThat(position).isEqualTo(0L);
        }
    }

    @Test
    public void testIsEndPositionIsTrueForPredefinedEndPositions() {
        Solution solution = new Solution();
        Board board = spy(Board.class);
        when(board.getEndPosition()).thenReturn(0B0000110L);
        solution.add(0B0000110L);
        assertThat(solution.isEndPositionValid(board)).isTrue();
    }

    @Test
    public void testIsEndPositionIsTrueForPositionsWithOnePeg() {
        Solution solution = new Solution();
        Board board = spy(Board.class);
        solution.add(0B0001100L)
                .add(0B0000010L);
        assertThat(solution.isEndPositionValid(board)).isTrue();
    }

    @Test
    public void testIsEndPositionIsFalseForPositionWithMultiplePegs() {
        Solution solution = new Solution();
        Board board = spy(Board.class);
        solution.add(0B0001100L)
                .add(0B0110100L);
        assertThat(solution.isEndPositionValid(board)).isFalse();
    }

    @Test
    public void testIsEndPositionIsFalseForEmptyPosition() {
        Solution solution = new Solution();
        Board board = spy(Board.class);
        solution.add(0L);
        assertThat(solution.isEndPositionValid(board)).isFalse();
    }

    @Test
    public void testCountMoves() {
        Solution solution = new Solution();

        // solution with 31 positions and only 15 moves for the english board
        solution.add(0B0000000_0000000_0000000_0001000_0000000_0000000_0000000L)
                .add(0B0000000_0001000_0001000_0000000_0000000_0000000_0000000L)
                .add(0B0000000_0001000_0000110_0000000_0000000_0000000_0000000L)
                .add(0B0000000_0001000_0000100_0000010_0000010_0000000_0000000L)
                .add(0B0000000_0001000_0000100_0000010_0001100_0000000_0000000L)
                .add(0B0000000_0001000_0001100_0001010_0000100_0000000_0000000L)
                .add(0B0000000_0001000_0001100_0001010_0011000_0000000_0000000L)
                .add(0B0000000_0001000_0001100_0001010_1101000_0000000_0000000L)
                .add(0B0000000_0001000_1001100_1001010_0101000_0000000_0000000L)
                .add(0B0000000_0001000_0111100_1001010_0101000_0000000_0000000L)
                .add(0B0010000_0011000_0101100_1001010_0101000_0000000_0000000L)
                .add(0B0001100_0011000_0101100_1001010_0101000_0000000_0000000L)
                .add(0B0001100_0011000_0101011_1001010_0101000_0000000_0000000L)
                .add(0B0001100_0011000_0101010_1001011_0101001_0000000_0000000L)
                .add(0B0001100_0001000_0111010_1011011_0101001_0000000_0000000L)
                .add(0B0001100_0001000_0111010_1001011_0111001_0010000_0000000L)
                .add(0B0001100_0001000_0111010_1001011_0111001_0001100_0000000L)
                .add(0B0001100_0001000_0111010_1001111_0111101_0001000_0000000L)
                .add(0B0001100_0001100_0111110_1001011_0111101_0001000_0000000L)
                .add(0B0001100_0001100_0111110_1001011_0111001_0001100_0000100L)
                .add(0B0001100_0001100_0111110_1001011_0111001_0001100_0011000L)
                .add(0B0011100_0011100_0101110_1001011_0111001_0001100_0011000L)
                .add(0B0011100_0011100_0111110_1011011_0101001_0001100_0011000L)
                .add(0B0011100_0011100_0111110_1011111_0101101_0001000_0011000L)
                .add(0B0011100_0011100_0111110_1011111_0101001_0001100_0011100L)
                .add(0B0011100_0011100_0111110_1011111_0100111_0001100_0011100L)
                .add(0B0011100_0011100_0111110_1011111_0011111_0001100_0011100L)
                .add(0B0011100_0011100_0111110_1011111_1101111_0001100_0011100L)
                .add(0B0011100_0011100_0111110_1001111_1111111_0011100_0011100L)
                .add(0B0011100_0011100_0111110_1110111_1111111_0011100_0011100L);

        EnglishBoard englishBoard = new EnglishBoard(new BitManipulator());
        Integer numMoves = solution.countMoves(englishBoard);
        assertThat(numMoves).isEqualTo(15);
    }

    @Test
    public void testToStringRendersAllPositions() {
        Solution solution = new Solution();
        Board board = spy(Board.class);

        solution.add(0B110L)
                .add(0B001L);

        when(board.renderPosition(0B001L)).thenReturn("• • ●\n");
        when(board.renderPosition(0B110L)).thenReturn("● ● •\n");

        assertThat(solution.toString(board)).isEqualTo("● ● •\n" +
                "• • ●\n" +
                "\n");
    }


}
