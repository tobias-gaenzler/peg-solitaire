package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This strategy uses a recursive depth first search method for finding a solution.
 * Symmetry is not taken into account. The first solution found is returned.
 */
@Component
public class DepthFirstStrategy implements SolutionStrategy {

    @Override
    public Solution solve(Board board, Long startPosition) {

        AtomicBoolean solved = new AtomicBoolean(false);
        Solution solution = new Solution(board.getNumberOfPegs(startPosition));

        // solve the game via recursion
        this.solveRecursive(startPosition, board, solution, solved);
        return solution;
    }

    /**
     * A depth first search method for finding a solution using recursion.
     *
     * @param startPosition solve the game for this start position
     * @param board         the board for which we solve the game
     * @param solution      the list of positions which is used by the solve method
     * @param solved        indicator for the recursive solve method to stop
     */
    private void solveRecursive(long startPosition, Board board, Solution solution, AtomicBoolean solved) {

        int numPieces = board.getNumberOfPegs(startPosition);
        // return if there is only one peg left
        if (numPieces <= 1) {
            return;
        }

        int numberOfMoves = solution.getPositions().size() - numPieces;

        // set current position in solution
        solution.getPositions().set(numberOfMoves, startPosition);

        // call this method for each consecutive position and return if a solution is found
        for (long position : board.getConsecutivePositions(startPosition)) {

            // set consecutive position
            solution.getPositions().set(numberOfMoves + 1, position);

            // return if we are finished
            if (solution.isEndPositionValid(board)) {
                solved.set(true);
                return;
            } else {
                this.solveRecursive(position, board, solution, solved);
            }

            // return if we are finished after recursive call
            if (solved.get()) {
                return;
            } else {
                // reset position, since no solution was found
                solution.getPositions().set(numberOfMoves + 1, 0L);
            }
        }
    }
}
