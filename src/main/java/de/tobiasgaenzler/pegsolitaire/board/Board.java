package de.tobiasgaenzler.pegsolitaire.board;


import java.util.List;
import java.util.Set;

/**
 * Interface to describe common methods of boards.
 * A Board is defined by a layout (a grid with holes for pegs).
 * The Board has a list of possible moves, knows which positions are symmetric and can
 * calculate consecutive positions of a start position.
 */
public interface Board {

    /**
     * This method returns the number of columns times number of rows
     * This number might not be equal to the number of usable holes on the board
     * => use getNumberOfPegs(layout) for number of pegs (i.e. all pegs set)
     * This method is mainly used for displaying the board as string.
     *
     * @return Integer of columns times number of rows
     */
    default Integer getNumberOfHoles() {
        return getColumns() * getRows();
    }


    /**
     * get number of columns of the grid
     *
     * @return Integer number of columns
     */
    Integer getColumns();


    /**
     * get number of rows of the grid
     *
     * @return Integer number of rows
     */
    Integer getRows();


    /**
     * Layout is a description of the holes which can be used, i.e. where a peg can be put
     * The layout is internally represented as a long where each bit corresponds to a field.
     *
     * @return the layout as long.
     */
    Long getLayout();


    /**
     * Internal representation of the start position as long.
     *
     * @return the default start position for this board as Long
     */
    Long getStartPosition();

    /**
     * Return the end position for this board. The english board has a defined end position.
     * Quadratic boards usually do not have a predefined end position, the game is solved, when there
     * remains only one piece.
     *
     * @return a position which indicates that the game is solved or null
     */
    default Long getEndPosition() {
        return null;
    }

    /**
     * get all positions which are symmetric to this position
     *
     * @param position find positions which are symmetric to this position
     * @return an array for all symmetric positions
     */
    long[] getSymmetricPositions(long position);

    /**
     * return a string representing for the position on this board
     * positions not in the layout are not displayed
     * positions where a peg is set are usually displayed as "O"
     * positions with no peg are usually displayed as "*"
     *
     * @param position Long the position on the board
     * @return the position as string
     */
    default String renderPosition(Long position) {
        return getPositionRenderer().renderToString(position, this);
    }

    PositionRenderer getPositionRenderer();

    /**
     * @return list of possible moves for this board
     */
    List<Move> getMoves();

    /**
     * get the masks to identify a connected move, i.e. two consecutive moves where the same peg moves twice
     *
     * @return set of masks
     */
    Set<Long> getConnectedMoveMasks();

    /**
     * return the number of bits which are set to one in position
     *
     * @param position long
     * @return number of bits which are set
     */
    default int getNumberOfPegs(long position) {
        return Long.bitCount(position);
    }

    /**
     * test bit at index in number. Used to check if a peg is present at position index.
     *
     * @param number long
     * @param index  position of bit (zero based)
     * @return true if bit at index is set
     */
    default boolean testBit(long number, int index) {
        return (number & 1L << index) != 0L;
    }

    /**
     * Name of the board (used by the {@code BoardFactory}).
     */
    String getName();

    /**
     * Apply all possible moves to the given position and return the resulting positions.
     *
     * @param position long the position to which the moves are applied.
     * @return return all consecutive positions,
     * i.e. all positions which are the result of applying one move to the given position
     */
    default long[] getConsecutivePositions(long position) {
        return getMoves().stream()//
                // apply mask to position and detect if move is possible
                // i.e.  masked equals two pegs which are adjacent to an empty slot
                .filter(move -> move.getCheck() == (position & move.getMask()))//
                // apply move, i.e. the two pegs are xor'ed to zero and the empty slot is xor'ed to one
                .mapToLong(move -> position ^ move.getMask())//
                .toArray();
    }

    /**
     * Assemble all possible moves on this board.
     * inspect the board from top left to right bottom for three consecutive holes.
     * Assemble masks for connected moves.
     */
    default void assembleMoves(List<Move> moves, Set<Long> connectedMoveMasks) {
        Long layout = getLayout();
        // find all possible moves for this board
        // order of traversal: top left to bottom right
        for (int row = getRows() - 1; row > -1; row--) {
            for (int column = getColumns() - 1; column > -1; column--) {
                // detect possible horizontal moves
                if ((row + 2 < getRows())
                        && testBit(layout, getPegId(row, column))
                        && testBit(layout, getPegId(row + 1, column))
                        && testBit(layout, getPegId(row + 2, column))) {
                    moves.add(new Move(getPegId(row, column), getPegId(row + 1, column), getPegId(row + 2, column)));
                    moves.add(new Move(getPegId(row + 2, column), getPegId(row + 1, column), getPegId(row, column)));
                }
                // detect possible vertical moves
                if ((column + 2 < getColumns())
                        && testBit(layout, getPegId(row, column))
                        && testBit(layout, getPegId(row, column + 1))
                        && testBit(layout, getPegId(row, column + 2))) {
                    moves.add(new Move(getPegId(row, column), getPegId(row, column + 1), getPegId(row, column + 2)));
                    moves.add(new Move(getPegId(row, column + 2), getPegId(row, column + 1), getPegId(row, column)));
                }
            }
        }

        // assemble the masks for detection of connected moves
        for (Move firstMove : moves) {
            for (Move secondMove : moves) {
                if (firstMove.getEnd() == secondMove.getStart() && ((firstMove.getCheck() & secondMove.getCheck()) == 0L)) {
                    connectedMoveMasks.add(firstMove.getMask() ^ secondMove.getMask());
                }
            }
        }
    }

    /**
     * Mapping of peg positions to id's: use bit order starting with the lowest bit
     * <ul>
     * <li>0 is bottom right corner (lowest bit)</li>
     * <li>rows from bottom to top</li>
     * <li>columns from right to left</li>
     * </ul>
     * Example:
     * <pre>
     *    (row, column)     |   peg ids
     *
     *   (2,2) (2,1) (2,0)  |   8 7 6
     *   (1,2) (1,1) (1,0)  |   5 4 3
     *   (0,2) (0,1) (0,0)  |   2 1 0
     * </pre>
     *
     * @param row    the peg's row
     * @param column the peg's column
     * @return int    the id of the peg
     */
    default int getPegId(int row, int column) {
        return (row * getRows() + column);
    }
}
