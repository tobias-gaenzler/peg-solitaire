package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;

import java.util.List;
import java.util.Set;

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
     * @return the winning positions (grouped by number of pegs on the board)
     */
    List<Set<Long>> solve(Board board, Long startPosition);
}
