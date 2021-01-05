package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QuadraticBoardSizeSixTest {
    private static final Logger logger = LoggerFactory.getLogger(QuadraticBoardSizeSixTest.class);

    private final Board board = new QuadraticBoardSizeSix(new BitManipulator());
    private final static int SIZE = 6;

    @Test
    public void testQuadraticBoardAttributes() {
        assertThat(board).isNotNull();
        assertThat(board.getColumns()).isEqualTo(SIZE);
        assertThat(board.getRows()).isEqualTo(SIZE);

        //  check layout and startPosition
        Long layout = 0B111111_111111_111111_111111_111111_111111L;
        assertThat(board.getLayout()).isEqualTo(layout);
        Long startPosition = 0B111111_111111_110111_111111_111111_111111L;
        assertThat(board.getStartPosition()).isEqualTo(startPosition);
    }

    @Test
    public void testRenderPosition() {
        String positionString =
                """
                                        
                ● ● ● ● ● ●\s
                ● ● ● ● ● ●\s
                ● ● • ● ● ●\s
                ● ● ● ● ● ●\s
                ● ● ● ● ● ●\s
                ● ● ● ● ● ●\s
                """;
        Long startPosition = board.getStartPosition();
        assertThat(board.renderPosition(startPosition)).isEqualTo(positionString);
    }

    @Test
    public void testGetSymmetricPositions() {
        long[] positions = board.getSymmetricPositions(0B101010_001100_100110_001101_110111_111001L);
        long[] expectedPositions = new long[8];
        // orig
        expectedPositions[0] = 0B101010001100100110001101110111111001L;
        // rot 180
        expectedPositions[1] = 0B100111111011101100011001001100010101L;
        // mirror horizontally
        expectedPositions[2] = 0B10101001100011001101100111011100111L;
        // mirror vertically
        expectedPositions[3] = 0B111001110111001101100110001100101010L;
        // mirror diag1
        expectedPositions[4] = 0B101011000011110101011110101010000111L;
        // mirror diag2
        expectedPositions[5] = 0B111000010101011110101011110000110101L;
        // rot 90
        expectedPositions[6] = 0B111101010011110110101000011101011L;
        // rot 270
        expectedPositions[7] = 0B110101110000101011011110010101111000L;
        // print positions to be able to visually control that the symmetric positions are correct
        Arrays.stream(expectedPositions).forEach(position -> logger.info(board.renderPosition(position)));
        assertThat(positions).isEqualTo(expectedPositions);
    }
}