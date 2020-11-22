package de.tobiasgaenzler.pegsolitaire.board;

/**
 * Presentation of a position in a matrix, top to bottom, left to right
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
