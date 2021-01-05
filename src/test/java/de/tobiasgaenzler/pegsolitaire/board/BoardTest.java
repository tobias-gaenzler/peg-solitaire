package de.tobiasgaenzler.pegsolitaire.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class BoardTest {

    Board board = spy(Board.class);

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
}