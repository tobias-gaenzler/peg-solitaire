package de.tobiasgaenzler.pegsolitaire.board;

/**
 * This class represents a move on a peg solitaire board (two adjacent pegs and hole, e.g. ● ● •)
 * The masks can be used to detect if a move is possible for a position and to apply this move to a position.
 * End and start are used to detect if moves are connected.
 */
public class Move {

    // the mask contains all three pegs
    private final long mask;

    // the check mask contains the first and the second peg
    private final long check;

    // the moving peg's position before the move
    private final long start;

    // the moving peg's position after the move
    private final long end;

    /**
     * @param start  index of the moving peg
     * @param center index of the peg which will be removed after the move
     * @param end    index of the peg after the move
     */
    public Move(int start, int center, int end) {
        // the mask contains all three pegs
        this.mask = (1L << start) | (1L << center) | (1L << end);
        // the check mask contains the first and the second peg
        this.check = (1L << start) | (1L << center);
        this.start = 1L << start;
        this.end = 1L << end;
    }

    /**
     * Getter for the move mask
     *
     * @return the move mask
     */
    public long getMask() {
        return mask;
    }

    /**
     * Getter for the check mask
     *
     * @return the check mask
     */
    public long getCheck() {
        return check;
    }

    /**
     * Getter for the moving peg's position before the move
     *
     * @return the moving peg's position before the move
     */
    public long getStart() {
        return start;
    }

    /**
     * Getter for the moving peg's position after the move
     *
     * @return the moving peg's position after the move
     */
    public long getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Move{" +
                "mask=" + mask +
                ", check=" + check +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (mask != move.mask) return false;
        if (check != move.check) return false;
        if (start != move.start) return false;
        return end == move.end;
    }

    @Override
    public int hashCode() {
        int result = (int) (mask ^ (mask >>> 32));
        result = 31 * result + (int) (check ^ (check >>> 32));
        result = 31 * result + (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));
        return result;
    }
}
