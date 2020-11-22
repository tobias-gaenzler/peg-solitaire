package de.tobiasgaenzler.pegsolitaire.solver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SolutionTest {

    private Solution solution = new Solution(32);

    @Test
    public void testSolutionInstantiation() {
        assertNotNull(solution);
        assertEquals(32, solution.getPositions().size());
        for (Long position : solution.getPositions()) {
            assertEquals((Long) 0L, position);
        }
    }
}
