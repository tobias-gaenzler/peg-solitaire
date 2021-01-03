package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holds data like moves and connected move masks for boards. Used in boards to favor composition over inheritance.
 */
public class BoardDataHolder {
    private final BitManipulator bitManipulator;
    private final List<Move> moves = new ArrayList<>();
    private final Set<Long> connectedMoveMasks = new HashSet<>();

    public BoardDataHolder(BitManipulator bitManipulator, Board board) {
        this.bitManipulator = bitManipulator;
        board.assembleMoves(moves, connectedMoveMasks);
    }

    public BitManipulator getBitManipulator() {
        return bitManipulator;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Set<Long> getConnectedMoveMasks() {
        return connectedMoveMasks;
    }
}
