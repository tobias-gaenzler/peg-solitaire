package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class EnglishBoardTest {

    private final static int SIZE = 7;
    private EnglishBoard board;

    @BeforeEach
    public void createBoard() {
        this.board = new EnglishBoard(new BitManipulator());
    }

    @Test
    public void testEnglishBoardAttributes() {
        assertThat(board).isNotNull();
        assertThat(board.getColumns()).isEqualTo(SIZE);
        assertThat(board.getRows()).isEqualTo(SIZE);

        //  check layout and startPosition
        Long layout = 0B0011100_0011100_1111111_1111111_1111111_0011100_0011100L;
        assertThat(board.getLayout()).isEqualTo(layout);
        Long startPosition = 0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L;
        assertThat(board.getStartPosition()).isEqualTo(startPosition);
    }

    @Test
    public void testRenderPosition() {
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
        Long startPosition = board.getStartPosition();
        assertThat(board.renderPosition(startPosition)).isEqualTo(positionString);
    }

    @Test
    public void testNumberOfPins() {
        assertThat(this.board.getNumberOfPegs(this.board.getStartPosition())).isEqualTo(32);
        assertThat(this.board.getNumberOfPegs(0L)).isEqualTo(0);
    }

    @Test
    public void testGetSymmetricPositions() {
        long[] positions = board.getSymmetricPositions(0B0010100_0001100_0111001_1001100_1101100_0011000_0001000L);
        long[] expectedPositions = new long[8];
        // orig
        expectedPositions[0] = 0B10100000110001110011001100110110000110000001000L;
        // rot 180
        expectedPositions[1] = 0B1000000110000110110011001100111000110000010100L;
        // mirror horizontally
        expectedPositions[2] = 0B10100001100010011100011001001101100011000001000L;
        // mirror vertically
        expectedPositions[3] = 0B1000001100011011001001100011100100011000010100L;
        // mirror diag1
        expectedPositions[4] = 0B11000001010001001011111110001101100000000000100L;
        // mirror diag2
        expectedPositions[5] = 0B10000000000011011000111111101001000101000001100L;
        // rot 90
        expectedPositions[6] = 0B100000000000110111111110010010100101000011000L;
        // rot 270
        expectedPositions[7] = 0B1100001010010100100111111110110000000000010000L;
        // print positions to be able to visually control that the symmetric positions are correct
        Arrays.stream(expectedPositions).forEach(position -> System.out.println(board.renderPosition(position)));
        assertThat(positions).isEqualTo(expectedPositions);
    }
}
