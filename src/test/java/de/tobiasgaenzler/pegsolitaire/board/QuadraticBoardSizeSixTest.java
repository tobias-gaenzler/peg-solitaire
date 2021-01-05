package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class QuadraticBoardSizeSixTest {
    private final Board board = new QuadraticBoardSizeSix(new BitManipulator());
    private final static int SIZE = 6;

    @Test
    public void testQuadraticBoardAttributes() {
        assertThat(board).isNotNull();
        assertThat(board.getColumns()).isEqualTo(SIZE);
        assertThat(board.getRows()).isEqualTo(SIZE);

        //  check layout and startPosition
        Long layout = 0B1111_1111_1111_1111L;
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
        expectedPositions[0] = 0B1011_0110_1001_1110L;
        // rot 180
        expectedPositions[1] = 0B0111_1001_0110_1101L;
        // mirror horizontally
        expectedPositions[2] = 0B1101_0110_1001_0111L;
        // mirror vertically
        expectedPositions[3] = 0B1110_1001_0110_1011L;
        // mirror diag1
        expectedPositions[4] = 0B1011_0101_1101_1010L;
        // mirror diag2
        expectedPositions[5] = 0B0101_1011_1010_1101L;
        // rot 90
        expectedPositions[6] = 0B1010_1101_0101_1011L;
        // rot 270
        expectedPositions[7] = 0B1101_1010_1011_0101L;
        // print positions to be able to visually control that the symmetric positions are correct
        Arrays.stream(expectedPositions).forEach(position -> System.out.println(board.renderPosition(position)));
        assertThat(positions).isEqualTo(expectedPositions);
    }
}