package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnglishBoardTest {

    private EnglishBoard englishBoard;

    @BeforeEach
    public void createBoard() {
        this.englishBoard = new EnglishBoard(new BitManipulator());
    }

    @Test
    public void testEnglishBoardAttributes() {
        assertNotNull(englishBoard);
        assertEquals((Integer) 7, englishBoard.getColumns());
        assertEquals((Integer) 7, englishBoard.getRows());
        assertEquals((Integer) 49, englishBoard.getNumberOfHoles());

        //  check layout and startPosition
        Long layout = 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
        assertEquals(layout, englishBoard.getLayout());
        Long startPosition = 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
        assertEquals(startPosition, englishBoard.getStartPosition());
    }

    @Test
    public void testCountMoves() {
        Solution solution = new Solution();

        // solution with 15 moves
        solution.add(0B0000000_0000000_0000000_0001000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0001000_0000000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0000110_0000000_0000000_0000000_0000000L);
        solution.add(0B0000000_0001000_0000100_0000010_0000010_0000000_0000000L);
        solution.add(0B0000000_0001000_0000100_0000010_0001100_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_0000100_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_0011000_0000000_0000000L);
        solution.add(0B0000000_0001000_0001100_0001010_1101000_0000000_0000000L);
        solution.add(0B0000000_0001000_1001100_1001010_0101000_0000000_0000000L);
        solution.add(0B0000000_0001000_0111100_1001010_0101000_0000000_0000000L);
        solution.add(0B0010000_0011000_0101100_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101100_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101011_1001010_0101000_0000000_0000000L);
        solution.add(0B0001100_0011000_0101010_1001011_0101001_0000000_0000000L);
        solution.add(0B0001100_0001000_0111010_1011011_0101001_0000000_0000000L);
        solution.add(0B0001100_0001000_0111010_1001011_0111001_0010000_0000000L);
        solution.add(0B0001100_0001000_0111010_1001011_0111001_0001100_0000000L);
        solution.add(0B0001100_0001000_0111010_1001111_0111101_0001000_0000000L);
        solution.add(0B0001100_0001100_0111110_1001011_0111101_0001000_0000000L);
        solution.add(0B0001100_0001100_0111110_1001011_0111001_0001100_0000100L);
        solution.add(0B0001100_0001100_0111110_1001011_0111001_0001100_0011000L);
        solution.add(0B0011100_0011100_0101110_1001011_0111001_0001100_0011000L);
        solution.add(0B0011100_0011100_0111110_1011011_0101001_0001100_0011000L);
        solution.add(0B0011100_0011100_0111110_1011111_0101101_0001000_0011000L);
        solution.add(0B0011100_0011100_0111110_1011111_0101001_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_0100111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_0011111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1011111_1101111_0001100_0011100L);
        solution.add(0B0011100_0011100_0111110_1001111_1111111_0011100_0011100L);
        solution.add(0B0011100_0011100_0111110_1110111_1111111_0011100_0011100L);
        Integer numMoves = solution.countMoves(englishBoard);
        assertEquals((Integer) 15, numMoves);
    }

    @Test
    public void testPositionToString() {
        String positionString =
                """
                                            
                    ● ● ●    \s
                    ● ● ●    \s
                ● ● ● ● ● ● ●\s
                ● ● ● • ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s
                """;
        assertThat(englishBoard.renderPosition(englishBoard.getStartPosition())).isEqualTo(positionString);
    }

    @Test
    public void testNumberOfPins() {
        assertEquals(32, this.englishBoard.getNumberOfPegs(this.englishBoard.getStartPosition()));
        assertEquals(0, this.englishBoard.getNumberOfPegs(0L));
    }

}
