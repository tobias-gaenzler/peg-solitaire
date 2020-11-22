package de.tobiasgaenzler.pegsolitaire.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoardFactoryTest {

    private BoardFactory testee = new BoardFactory();

    @Test
    public void testEnglishBoardCreation() {
        Board board = testee.createEnglishBoard();
        assertThat(board.getName()).isEqualTo(EnglishBoard.NAME);
    }

    @Test
    public void testQuadraticBoardCreation() {
        Board board = testee.createQuadraticBoard(4);
        assertNotNull(board);
        assertThat(board.getColumns()).isEqualTo(4);
        assertThat(board.getRows()).isEqualTo(4);
        board = testee.createQuadraticBoard(6);
        assertNotNull(board);
        assertThat(board.getColumns()).isEqualTo(6);
        assertThat(board.getRows()).isEqualTo(6);
    }
}
