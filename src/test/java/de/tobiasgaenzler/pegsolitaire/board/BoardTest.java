package de.tobiasgaenzler.pegsolitaire.board;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class BoardTest {

    private final Board board = spy(Board.class);

    @Test
    public void testGetNumberOfPegsReturnsTwoForPositionWithTwoPegs() {
        assertThat(board.getNumberOfPegs(0B001100L)).isEqualTo(2);
    }

    @Test
    public void testBitIndexIsZeroBased() {
        assertThat(board.testBit(0b01, 0)).isTrue();
    }

    @Test
    public void testGetPegIdReturnsCorrectIDForThreeRows() {
        when(board.getRows()).thenReturn(3);
        assertThat(board.getPegId(0,0)).isEqualTo(0);
        assertThat(board.getPegId(0,1)).isEqualTo(1);
        assertThat(board.getPegId(1,1)).isEqualTo(4);
        assertThat(board.getPegId(2,2)).isEqualTo(8);
    }

    @Test
    public void testAssembleMovesForQuadraticBoardSizeThree() {
        when(board.getRows()).thenReturn(3);
        when(board.getColumns()).thenReturn(3);
        when(board.getLayout()).thenReturn(0B111_111_111L);
        List<Move> moves = new ArrayList<>();
        List<Move> expectedMoves =
                List.of(
                new Move(6,7,8),
                new Move(8,7,6),
                new Move(3,4,5),
                new Move(5,4,3),
                new Move(2,5,8),
                new Move(8,5,2),
                new Move(1,4,7),
                new Move(7,4,1),
                new Move(0,3,6),
                new Move(6,3,0),
                new Move(0,1,2),
                new Move(2,1,0)
                );
        Set<Long> connectedMoveMasks = new HashSet<>();
        Set<Long> expectedConnectedMoveMasks = Set.of(0B100_100_011L, 0B011_100_100L, 0B110_001_001L, 0B001_001_110L);
        board.assembleMoves(moves, connectedMoveMasks);
        assertThat(moves).isEqualTo(expectedMoves);
        assertThat(connectedMoveMasks).isEqualTo(expectedConnectedMoveMasks);
    }
    @Test
    public void testGetConsecutivePositionsForQuadraticBoardSizeThree() {
        when(board.getRows()).thenReturn(3);
        when(board.getColumns()).thenReturn(3);
        when(board.getLayout()).thenReturn(0B111_111_111L);

        List<Move> moves = new ArrayList<>();
        Set<Long> connectedMoveMasks = new HashSet<>();
        when(board.getMoves()).thenReturn(moves);
        board.assembleMoves(moves, connectedMoveMasks);

        long[] consecutivePositions = board.getConsecutivePositions(0B110_011_111L);
        long[] expectedConsecutivePositions = new long[3];
        expectedConsecutivePositions[0] = 0B001_011_111L;
        expectedConsecutivePositions[1] = 0B110_100_111L;
        expectedConsecutivePositions[2] = 0B111_010_110L;

        assertThat(consecutivePositions).isEqualTo(expectedConsecutivePositions);
    }
}