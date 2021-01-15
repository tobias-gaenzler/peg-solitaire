package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QuadraticBoardSizeFiveTest {
    private static final Logger logger = LoggerFactory.getLogger(QuadraticBoardSizeFiveTest.class);

    private final Board board = new QuadraticBoardSizeFive(new BitManipulator(), new PositionRenderer());
    private final static int SIZE = 5;

    @Test
    public void testQuadraticBoardAttributes() {
        assertThat(board).isNotNull();
        assertThat(board.getColumns()).isEqualTo(SIZE);
        assertThat(board.getRows()).isEqualTo(SIZE);

        //  check layout and startPosition
        Long layout = 0B11111_11111_11111_11111_11111L;
        assertThat(board.getLayout()).isEqualTo(layout);
        Long startPosition = 0B11111_11111_11111_11011_11111L;
        assertThat(board.getStartPosition()).isEqualTo(startPosition);
    }

    @Test
    public void testRenderPosition() {
        board.getPositionRenderer().usePrettyLayout();
        String positionString =
                """
                                                
                        ● ● ● ● ●\s
                        ● ● ● ● ●\s
                        ● ● ● ● ●\s
                        ● ● • ● ●\s
                        ● ● ● ● ●\s
                        """;
        Long startPosition = board.getStartPosition();
        assertThat(board.renderPosition(startPosition)).isEqualTo(positionString);
    }

    @Test
    public void testGetSymmetricPositions() {
        board.getPositionRenderer().usePrettyLayout();
        long[] positions = board.getSymmetricPositions(0B00111_10101_11011_01011_11100L);
        long[] expectedPositions = new long[8];
        // orig
        expectedPositions[0] = 0B11110101110110101111100L;
        // rot 180
        expectedPositions[1] = 0B11111010110111010111100L;
        // mirror horizontally
        expectedPositions[2] = 0B1110010101110111101000111L;
        // mirror vertically
        expectedPositions[3] = 0B1110001011110111010100111L;
        // mirror diag1
        expectedPositions[4] = 0B110100111110011011011110L;
        // mirror diag2
        expectedPositions[5] = 0B111101101100111110010110L;
        // rot 90
        expectedPositions[6] = 0B1111010110110010011101101L;
        // rot 270
        expectedPositions[7] = 0B1011011100100110110101111L;
        // print positions to be able to visually control that the symmetric positions are correct
        Arrays.stream(expectedPositions).forEach(position -> logger.info(board.renderPosition(position)));
        assertThat(positions).isEqualTo(expectedPositions);
    }
}