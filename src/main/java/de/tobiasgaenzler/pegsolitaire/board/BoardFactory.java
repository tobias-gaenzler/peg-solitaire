package de.tobiasgaenzler.pegsolitaire.board;

import org.springframework.stereotype.Component;

@Component
public class BoardFactory {

    /**
     * @return an english board
     */
    public Board createEnglishBoard() {
        return new EnglishBoard();
    }

    /**
     * board creation of quadratic boards. Use the boardID "quadratic" and a size to create a quadratic board.
     *
     * @param size the size of the quadratic board.
     * @return a quadratic board if the boardID matches, null otherwise
     */
    public Board createQuadraticBoard(Integer size) {
        if (size == null || size < 3 || size > 6) {
            return null;
        }
        return new QuadraticBoard(size);
    }
}
