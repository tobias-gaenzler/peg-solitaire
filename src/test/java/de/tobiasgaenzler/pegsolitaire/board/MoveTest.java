package de.tobiasgaenzler.pegsolitaire.board;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveTest {

    @Test
    public void testMoveAttributes() {
        // a move from 6 to 4 removing 5
        int start = 6;
        int center = 5;
        int end = 4;

        Move move = new Move(start, center, end);
        long mask = 0B0111_0000L;
        long check = 0B0110_0000L;

        assertThat(move.getMask()).isEqualTo(mask);
        assertThat(move.getCheck()).isEqualTo(check);
        assertThat(move.getStart()).isEqualTo(0B0100_0000L);
        assertThat(move.getEnd()).isEqualTo(0B0001_0000L);
    }
}
