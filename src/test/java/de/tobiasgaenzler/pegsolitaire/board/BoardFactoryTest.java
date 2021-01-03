package de.tobiasgaenzler.pegsolitaire.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class BoardFactoryTest {

    @Autowired
    private BoardFactory boardFactory;

    @Test
    public void testEnglishBoardCreation() {
        Board board = boardFactory.getBoard(EnglishBoard.NAME);
        assertThat(board.getName()).isEqualTo(EnglishBoard.NAME);
    }

    @Test
    public void testQuadraticBoardSizeFourCreation() {
        Board board = boardFactory.getBoard(QuadraticBoardSizeFour.NAME);
        assertNotNull(board);
        assertThat(board.getColumns()).isEqualTo(4);
        assertThat(board.getRows()).isEqualTo(4);
    }

    @Test
    public void testQuadraticBoardSizeFiveCreation() {
        Board board = boardFactory.getBoard(QuadraticBoardSizeFive.NAME);
        assertNotNull(board);
        assertThat(board.getColumns()).isEqualTo(5);
        assertThat(board.getRows()).isEqualTo(5);
    }

    @Test
    public void testQuadraticBoardSizeSixCreation() {
        Board board = boardFactory.getBoard(QuadraticBoardSizeSix.NAME);
        assertNotNull(board);
        assertThat(board.getColumns()).isEqualTo(6);
        assertThat(board.getRows()).isEqualTo(6);
    }
}
