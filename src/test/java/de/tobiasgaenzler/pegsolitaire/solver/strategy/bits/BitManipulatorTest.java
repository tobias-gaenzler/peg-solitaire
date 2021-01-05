package de.tobiasgaenzler.pegsolitaire.solver.strategy.bits;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BitManipulatorTest {

    private final BitManipulator bitManipulator = new BitManipulator();

    @Test
    public void testSwapShouldSwapBlockOfLengthThree() {
        assertThat(bitManipulator.swap(1, 5, 3, 0B0010_1111L)).isEqualTo(0B1110_0011L);
    }

    @Test
    public void testSwapShouldMirrorVerticallyOnQuadraticBoardSize4() {
        long position = 0B1011_0110_1001_1110L;
        long positionMirroredVertically = 0B1110_1001_0110_1011L;

        long firstRowSwappedWithLastRow = bitManipulator.swap(0, 12, 4, position);
        long mirroredVertically = bitManipulator.swap(4, 8, 4,
                firstRowSwappedWithLastRow);

        assertThat(mirroredVertically).isEqualTo(positionMirroredVertically);
    }

    @Test
    public void testSwapShouldThrowErrorOnOverlappingBlocks() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                bitManipulator.swap(1, 3, 3, 0B0010_1111L));
        assertThat(exception.getMessage()).contains("Blocks overlap");
    }

    @Test
    public void testSwapShouldThrowErrorWhenOutOfLongRange() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                bitManipulator.swap(1, 60, 10, 0B0010_1111L));
        assertThat(exception.getMessage()).contains("Out of range");
    }
}
