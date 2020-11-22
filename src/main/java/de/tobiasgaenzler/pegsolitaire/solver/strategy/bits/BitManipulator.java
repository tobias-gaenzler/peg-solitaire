package de.tobiasgaenzler.pegsolitaire.solver.strategy.bits;

import org.springframework.stereotype.Component;

/**
 * Provides method for swapping bits in a position.
 * Used by QuadraticBoard to mirror positions (e.g. swap top row with bottom row).
 * See http://graphics.stanford.edu/~seander/bithacks.html#SwappingBitsXOR.
 */
@Component
public class BitManipulator {

    /**
     * Swap some consecutive bits (block) with others.
     * Example: position = 001 0 111 1 where numberOfBits = 3, startPositionFirstBlock = 1 and startPositionSecondBlock = 5.
     * Swap the three consecutive bits starting at bit 1 (that gives us the block the '111')
     * with three consecutive bits starting at bit 5 (that gives us the block '001').
     * The result is 111 0 001 1
     *
     * @return the resulting long where the blocks of bits have been swapped.
     */
    public long swap(int startPositionFirstBlock, int startPositionSecondBlock, int numberOfBits, long position) {
        // would possibly overflow a long value
        if (startPositionFirstBlock + numberOfBits > 63 || startPositionSecondBlock + numberOfBits > 63) {
            throw new IllegalArgumentException("Out of range: can not swap bits in a 64 bit long for 'startPositionFirstBlock="
                    + startPositionFirstBlock + ", startPositionSecondBlock=" + startPositionSecondBlock + ", numberOfBits=" + numberOfBits);
        }
        if(startPositionFirstBlock + numberOfBits > startPositionSecondBlock) {
            throw new IllegalArgumentException("Blocks overlap: can not swap bits in a 64 bit long for 'startPositionFirstBlock="
                    + startPositionFirstBlock + ", startPositionSecondBlock=" + startPositionSecondBlock + ", numberOfBits=" + numberOfBits);
        }
        /* xor contains xor of two sets */
        long xor = ((position >> startPositionFirstBlock) ^ (position >> startPositionSecondBlock)) & ((1L << numberOfBits) - 1L);

        /* To swap two sets, we need to again XOR the xor with original sets */
        return position ^ ((xor << startPositionFirstBlock) | (xor << startPositionSecondBlock));
    }
}
