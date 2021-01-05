package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * A quadratic board of size six. The board has 36 holes (6x6) and a "full" layout, i.e. every hole is usable.
 * Start position has two holes (all other positions are pegs) since positions with only one hole do not have a solution).
 * No end position is defined (every position with one peg is a solution).
 */
@Component
public class QuadraticBoardSizeSix implements Board {

    public static final String NAME = "Quadratic Board Size Six";
    private final BoardDataHolder boardDataHolder;
    private final Integer size = 6;

    /**
     * Default constructor which assembles possible moves.
     */
    @Autowired
    public QuadraticBoardSizeSix(BitManipulator bitManipulator) {
        super();
        boardDataHolder = new BoardDataHolder(bitManipulator, this);
    }

    @Override
    public String renderPosition(Long position) {
        return new PositionRenderer(new PositionTransformer()).renderToString(position, this);
    }

    @Override
    public List<Move> getMoves() {
        return boardDataHolder.getMoves();
    }

    @Override
    public Set<Long> getConnectedMoveMasks() {
        return boardDataHolder.getConnectedMoveMasks();
    }

    @Override
    public Integer getNumberOfHoles() {
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
        // arbitrary position which has a solution.
        // ● ● ● ● ● ●
        // ● ● ● ● ● ●
        // ● ● • ● ● ●
        // ● ● ● ● ● ●
        // ● ● ● ● ● ●
        // ● ● ● ● ● ●
        return 0B111111_111111_110111_111111_111111_111111L;
    }

    /**
     * No specific end position is defined (every position with one peg is a solution).
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
        return NAME;
    }


    private long mirrorDiagonally(long position) {
        BitManipulator bitManipulator = boardDataHolder.getBitManipulator();
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

    private long mirrorVertically(long position) {
        BitManipulator bitManipulator = boardDataHolder.getBitManipulator();
        return bitManipulator.swap(12, 18, 6, bitManipulator.swap(6, 24, 6, bitManipulator.swap(0, 30, 6, position)));
    }

    private long rotateBy180(long position) {
        return Long.reverse(position) >>> (64 - getNumberOfHoles());
    }
}
