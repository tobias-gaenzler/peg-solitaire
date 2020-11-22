package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A quadratic board implementation of board. Only sizes 4,5 and 6 are supported
 * (smaller sizes do not have solutions, bigger sizes take too long to compute).
 * The board has dimension size x size and a "full" layout, i.e. every hole is usable.
 * Quadratic boards have no predefined end position.
 */
public class QuadraticBoard implements Board {

    public static final String NAME = "Quadratic Board";
    private final List<Move> moves = new ArrayList<>();
    private final Set<Long> connectedMoveMasks = new HashSet<>();
    // size of the board (width == height)
    private final Integer size;
    private final BitManipulator bitManipulator = new BitManipulator();

    /**
     * default constructor with size
     * assemble possible moves
     *
     * @param size the size of the board (width==height)
     */
    public QuadraticBoard(Integer size) {
        super();
        this.size = size;
        assembleMoves(moves, connectedMoveMasks);
    }

    @Override
    public String toString(Long position) {
        return new PositionRenderer(new PositionTransformer()).renderToString(position, this);
    }

    @Override
    public List<Move> getMoves() {
        return moves;
    }

    @Override
    public Set<Long> getConnectedMoveMasks() {
        return connectedMoveMasks;
    }

    @Override
    public Integer getNumberOfHoles() {
        // size^2
        return size * size;
    }

    @Override
    public Integer getColumns() {
        return size;
    }

    @Override
    public Integer getRows() {
        return size;
    }

    /**
     * @return layout where every peg is set
     */
    @Override
    public Long getLayout() {
        long layout = 0L;
        for (int n = 0; n < getNumberOfHoles(); n++) {
            layout |= 1L << n;
        }
        return layout;
    }

    /**
     * @return a solvable start position
     */
    @Override
    public Long getStartPosition() {
        if (size == 4) {
            return 0B1101_1111_1111_1111L;
        }
        if (size == 5) {
            return 0B11111_11111_11111_11011_11111L;
        }
        if (size == 6) {
            return 0B111111_111111_110111_111111_111111_111111L;
        }
        long startPosition = getLayout();
        for (int i = 0; i < getColumns(); i++) {
            long position = 1L << (i * getColumns() + i);
            startPosition = startPosition ^ position;
        }
        return startPosition;
    }

    /**
     * for a quadratic board there is usually no predefined end position
     *
     * @return null
     */
    @Override
    public Long getEndPosition() {
        return null;
    }

    /**
     * We have eight symmetric positions:
     * the position itself
     * rotated by 90 degree
     * rotated by 180 degree
     * rotated by 270 degree
     * vertical mirror
     * horizontal mirror
     * diagonal mirror on d1
     * diagonal mirror on d2
     * Since rotating by 180 degree is inexpensive, use it to calculate other symmetric positions
     */
    @Override
    public long[] getSymmetricPositions(long position) {
        long[] symmetricPositions = new long[8];
        symmetricPositions[0] = position;
        long rotatedBy180 = rotateBy180(position);
        symmetricPositions[1] = rotatedBy180;
        symmetricPositions[2] = mirrorVertically(rotatedBy180);
        symmetricPositions[3] = mirrorVertically(position);

        long mirroredDiagonally = mirrorDiagonally(position);
        symmetricPositions[4] = mirroredDiagonally;
        symmetricPositions[5] = rotateBy180(mirroredDiagonally);
        long temp = mirrorVertically(mirroredDiagonally);
        symmetricPositions[6] = temp;
        symmetricPositions[7] = rotateBy180(temp);
        return symmetricPositions;
    }

    @Override
    public String getName() {
        return NAME + " (" + getColumns() + " x " + getColumns() + ")";
    }


    private long mirrorDiagonally(long position) {
        if (size == 4) {
            long temp = bitManipulator.swap(11, 14, 1, position);
            temp = bitManipulator.swap(7, 13, 1, temp);
            temp = bitManipulator.swap(3, 12, 1, temp);
            temp = bitManipulator.swap(2, 8, 1, temp);
            temp = bitManipulator.swap(1, 4, 1, temp);
            return bitManipulator.swap(6, 9, 1, temp);
        } else if (size == 5) {
            long temp = bitManipulator.swap(19, 23, 1, position);
            temp = bitManipulator.swap(14, 22, 1, temp);
            temp = bitManipulator.swap(9, 21, 1, temp);
            temp = bitManipulator.swap(4, 20, 1, temp);
            temp = bitManipulator.swap(13, 17, 1, temp);
            temp = bitManipulator.swap(8, 16, 1, temp);
            temp = bitManipulator.swap(3, 15, 1, temp);
            temp = bitManipulator.swap(7, 11, 1, temp);
            temp = bitManipulator.swap(2, 10, 1, temp);
            return bitManipulator.swap(1, 5, 1, temp);
        } else if (size == 6) {
            long temp = bitManipulator.swap(29, 34, 1, position);
            temp = bitManipulator.swap(23, 33, 1, temp);
            temp = bitManipulator.swap(17, 32, 1, temp);
            temp = bitManipulator.swap(11, 31, 1, temp);
            temp = bitManipulator.swap(5, 30, 1, temp);
            temp = bitManipulator.swap(22, 27, 1, temp);
            temp = bitManipulator.swap(16, 26, 1, temp);
            temp = bitManipulator.swap(10, 25, 1, temp);
            temp = bitManipulator.swap(4, 24, 1, temp);
            temp = bitManipulator.swap(15, 20, 1, temp);
            temp = bitManipulator.swap(9, 19, 1, temp);
            temp = bitManipulator.swap(3, 18, 1, temp);
            temp = bitManipulator.swap(8, 13, 1, temp);
            temp = bitManipulator.swap(2, 12, 1, temp);
            return bitManipulator.swap(1, 6, 1, temp);
        }
        throw new RuntimeException("Board size other than 4,5 or 6 not implemented yet");
    }

    private long mirrorVertically(long position) {
        if (size == 4) {
            return bitManipulator.swap(4, 8, 4, bitManipulator.swap(0, 12, 4, position));
        } else if (size == 5) {
            return bitManipulator.swap(5, 15, 5, bitManipulator.swap(0, 20, 5, position));
        } else if (size == 6) {
            return bitManipulator.swap(12, 18, 6, bitManipulator.swap(6, 24, 6, bitManipulator.swap(0, 30, 6, position)));
        }
        throw new RuntimeException("Board size other than 4,5 or 6 not implemented yet");
    }

    private long rotateBy180(long position) {
        return Long.reverse(position) >>> (64 - getNumberOfHoles());
    }
}
