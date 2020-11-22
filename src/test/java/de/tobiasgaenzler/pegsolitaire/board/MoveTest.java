package de.tobiasgaenzler.pegsolitaire.board;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    @Test
    public void testMoveAttributes() {
        int start = 6;
        int center = 5;
        int end = 4;
        Move move = new Move(start, center, end);
        long mask = (1L << start) | (1L << center) | (1L << end);
        long check = (1L << start) | (1L << center);
        assertEquals(mask, move.getMask());
        assertEquals(check, move.getCheck());
        assertEquals(1L << start, move.getStart());
        assertEquals(1L << end, move.getEnd());
    }
}
