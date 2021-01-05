package de.tobiasgaenzler.pegsolitaire.board;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.tobiasgaenzler.pegsolitaire.board.PositionContent.*;

/**
 * Provide a presentation of a position on a board as
 * <ul>
 *     <li>String (used for printing on console)</li>
 *     <li>Matrix (can be used for advanced graphical representation)</li>
 * </ul>
 */
public class PositionRenderer {

    public static final String SPACE = "\u0020";
    private final PositionTransformer positionTransformer;

    public PositionRenderer() {
        this.positionTransformer = new PositionTransformer();
    }

    /**
     * Return a matrix presentation of the position on the board.
     */
    public List<List<PositionContent>> renderToMatrix(Long position, Board board) {
        return getRows(position, board, positionTransformer);
    }

    /**
     * Return a string representation of the position on the board.
     */
    public String renderToString(Long position, Board board) {
        List<List<PositionContent>> presentation = renderToMatrix(position, board);
        return presentation.stream().map(row -> //
                row.stream()//
                        .map(this::getStringRepresentation)//
                        .collect(Collectors.joining(SPACE))//
        ).collect(Collectors.joining(SPACE + "\n", "\n", SPACE + "\n"));
    }


    /**
     * Return the board content in the order suitable for presentation.
     * A string builder usually starts at the top left corner and traverses the board from left to right.
     *
     * @return rows in descending order (starting with top row).
     * columns in descending order (starting at left).
     */
    private List<List<PositionContent>> getRows(long position, Board board, PositionTransformer positionTransformer) {
        List<List<PositionContent>> rows = new ArrayList<>();
        // initialize list (enables access in correct order)
        IntStream.range(0, board.getRows())//
                .forEach(row -> rows.add(new ArrayList<>()));

        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                PositionContent content = getContent(new MatrixPosition(row, column), position, board, positionTransformer);
                rows.get(row).add(content);
            }
        }
        return rows;
    }

    private PositionContent getContent(MatrixPosition matrixPosition, long position, Board board, PositionTransformer positionTransformer) {
        MatrixPosition internalMatrixPosition = positionTransformer.transformToInternal(board, matrixPosition);
        int id = board.getPegId(internalMatrixPosition.getRow(), internalMatrixPosition.getColumn());
        if (!board.testBit(board.getLayout(), id)) {
            return UNUSED;
        } else if (board.testBit(position, id)) {
            return PEG;
        } else {
            return HOLE;
        }
    }

    private String getStringRepresentation(PositionContent positionContent) {
        if (positionContent.isPeg()) {
            return "\u25CF";
        } else if (positionContent.isHole()) {
            return "\u2022";
        } else {
            return SPACE;
        }
    }
}
