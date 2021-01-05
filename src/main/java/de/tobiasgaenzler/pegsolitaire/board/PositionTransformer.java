package de.tobiasgaenzler.pegsolitaire.board;

/**
 * Transforms positions between presentation (suitable for printing, matrix, left to right, top to bottom)
 * and internal representation for calculations (matrix, bottom to top, right to left)
 */
public class PositionTransformer {

    public MatrixPosition transformToPresentation(Board board, MatrixPosition matrixPosition) {
        return new MatrixPosition(board.getRows() - matrixPosition.getRow() - 1, board.getColumns() - 1 - matrixPosition.getColumn());
    }

    public MatrixPosition transformToInternal(Board board, MatrixPosition matrixPosition) {
        return transformToPresentation(board, matrixPosition);
    }
}
