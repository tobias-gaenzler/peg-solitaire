package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;

/**
 * Interface for strategies which can be used to find a single solution to the solitaire game.
 */
public interface SingleSolutionStrategy {

    /**
     * Find a single solution for a given start position on a board.
     *
     * @param board         the board for the game
     * @param startPosition find single solution for this start position
     * @return a solution if found, null if not
     */
    Solution solve(Board board, Long startPosition);

    String getName();
}
