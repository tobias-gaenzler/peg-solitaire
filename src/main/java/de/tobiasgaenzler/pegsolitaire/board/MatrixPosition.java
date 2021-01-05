package de.tobiasgaenzler.pegsolitaire.board;

/**
 * Presentation of a position in a matrix, top to bottom, left to right.
 * Example: in a matrix with four rows and columns (quadratic board size 4)
 * the position (3,1) is the peg in row three and column 1
 */
public class MatrixPosition {
    private final Integer row;
    private final Integer column;

    public MatrixPosition(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }
}
