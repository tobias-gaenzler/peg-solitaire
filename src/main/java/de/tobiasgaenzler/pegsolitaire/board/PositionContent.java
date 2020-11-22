package de.tobiasgaenzler.pegsolitaire.board;

/**
 * Enum which describes what a position on a board can hold:
 * PEG, HOLE or UNUSED
 */
public enum PositionContent {
    PEG,
    HOLE,
    UNUSED;

    public boolean isPeg() {
        return PEG.equals(this);
    }

    public boolean isHole() {
        return HOLE.equals(this);
    }
}
