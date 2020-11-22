package de.tobiasgaenzler.pegsolitaire.solver;


import de.tobiasgaenzler.pegsolitaire.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * A Solution is just a list of consecutive positions with some additional convenience methods.
 */
public class Solution {
    private final List<Long> positions;

    /**
     * Default constructor initializes the position list.
     */
    public Solution() {
        this.positions = new ArrayList<>();
    }

    /**
     * Initialize all positions with zero.
     *
     * @param size the size of the position list.
     */
    public Solution(int size) {
        this.positions = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            add(0L);
        }
    }

    /**
     * a method for adding a position.
     *
     * @param position the position to add
     * @return this object
     */
    public Solution add(Long position) {
        positions.add(position);
        return this;
    }

    /**
     * Getter for the position list
     *
     * @return the list of positions
     */
    public List<Long> getPositions() {
        return this.positions;
    }

    /**
     * count moves for this solution on board
     *
     * @param board the board the positions live on
     * @return the number of moves for this solution
     */
    public Integer countMoves(Board board) {
        //first move is always a new move
        int numMoves = 1;
        for (int i = 0; i < positions.size() - 2; i++) {
            long pos1 = positions.get(i);
            long pos2 = positions.get(i + 1);
            long pos3 = positions.get(i + 2);

            long mask = pos1 ^ pos3;
            if ((((pos1 ^ pos2) | (pos2 ^ pos3)) & (pos1 & pos3)) != 0L || !board.getConnectedMoveMasks().contains(mask)) {
                numMoves++;
            }
        }
        return numMoves;
    }

    /**
     * Return true if the last position of this solution is considered a valid end position.
     * For boards with predefined end position check if the solutions end position equals
     * the boards end position.
     * For all other boards check if the last position has only one piece.
     *
     * @param board the board, the solution lives on
     * @return true   if this solution is a solution for the board board.
     */
    public boolean isEndPositionValid(Board board) {
        long endPosition = positions.get(positions.size() - 1);
        if (board.getEndPosition() != null) {
            return (board.getEndPosition().equals(endPosition));
        } else if (endPosition != 0L) {
            return board.getNumberOfPegs(endPosition) == 1;
        }
        return false;
    }

    /**
     * pretty print this solution using the board
     *
     * @param board the board, the positions live on
     * @return a string representing the solution in the board
     */
    public String toString(Board board) {
        StringBuilder solutionString = new StringBuilder();
        positions.forEach(position -> {
            String positionString = board.toString(position);
            solutionString.append(positionString);
        });
        solutionString.append("\n");
        return solutionString.toString();
    }
}
