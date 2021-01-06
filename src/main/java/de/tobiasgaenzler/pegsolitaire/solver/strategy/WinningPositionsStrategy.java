package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface for finding all winning positions for a solitaire game.
 * A winning position is a position from which the game can still be solved.
 * From the winning positions all solutions can be derived.
 */
public interface WinningPositionsStrategy {

    /**
     * Find all winning positions for a certain start position on a board.
     *
     * @param board         the board for the game
     * @param startPosition find winning positions for this start position
     * @return the paths to the files with the winning positions (first is start position, grouped by number of pegs on the board)
     */
    List<Path> solve(Board board, Long startPosition);
}
