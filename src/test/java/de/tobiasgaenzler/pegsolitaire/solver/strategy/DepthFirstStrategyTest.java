package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.board.QuadraticBoardSizeFive;
import de.tobiasgaenzler.pegsolitaire.board.QuadraticBoardSizeFour;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepthFirstStrategyTest {

    @Test
    public void testDepthFirstStrategyForQuadraticBoardSizeFour() {
        // depth first is deterministic and always returns the same solution.
        // prefer visual representation (better documentation and easier to grasp if really a solution).
        String expectedSolution = """
                                
                ● ● ● •\s
                ● • ● ●\s
                ● ● ● ●\s
                ● ● ● ●\s

                ● • • ●\s
                ● • ● ●\s
                ● ● ● ●\s
                ● ● ● ●\s

                ● • • ●\s
                ● ● • •\s
                ● ● ● ●\s
                ● ● ● ●\s

                ● • • ●\s
                • • ● •\s
                ● ● ● ●\s
                ● ● ● ●\s

                ● • ● ●\s
                • • • •\s
                ● ● • ●\s
                ● ● ● ●\s

                ● ● • •\s
                • • • •\s
                ● ● • ●\s
                ● ● ● ●\s

                • • ● •\s
                • • • •\s
                ● ● • ●\s
                ● ● ● ●\s

                • • ● •\s
                • • • •\s
                • • ● ●\s
                ● ● ● ●\s

                • • ● •\s
                • • ● •\s
                • • • ●\s
                ● ● • ●\s

                • • • •\s
                • • • •\s
                • • ● ●\s
                ● ● • ●\s

                • • • •\s
                • • • •\s
                • ● • •\s
                ● ● • ●\s

                • • • •\s
                • • • •\s
                • ● • •\s
                • • ● ●\s

                • • • •\s
                • • • •\s
                • ● • •\s
                • ● • •\s

                • • • •\s
                • ● • •\s
                • • • •\s
                • • • •\s

                """;
        Board board = new QuadraticBoardSizeFour(new BitManipulator());
        SolutionStrategy strategy = new DepthFirstStrategy();
        long startPosition = 0B1110_1011_1111_1111L;

        Solution solution = strategy.solve(board, startPosition);

        assertThat(solution.toString(board)).isEqualTo(expectedSolution);
    }

    @Test
    public void testDepthFirstStrategyForQuadraticBoardSizeFive() {
        // depth first is deterministic and always returns the same solution.
        // prefer visual representation (better documentation and easier to grasp if really a solution).
        String expectedSolution = """
                                
                ● ● ● ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s

                ● ● ● ● ●\s
                ● ● • ● ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                ● ● ● ● ●\s
                • • ● ● ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                ● ● ● ● ●\s
                • ● • • ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                ● ● ● ● ●\s
                • ● • • ●\s
                • • ● ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                ● • ● ● ●\s
                • • • • ●\s
                • ● ● ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                ● ● • • ●\s
                • • • • ●\s
                • ● ● ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                • • ● • ●\s
                • • • • ●\s
                • ● ● ● ●\s
                ● ● ● ● ●\s
                ● ● ● ● ●\s

                • • ● • ●\s
                • • ● • ●\s
                • ● • ● ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s

                • • • • ●\s
                • • • • ●\s
                • ● ● ● ●\s
                ● ● • ● ●\s
                ● ● ● ● ●\s

                • • • • ●\s
                • • • • ●\s
                • ● ● ● ●\s
                • • ● ● ●\s
                ● ● ● ● ●\s

                • • • • ●\s
                • • ● • ●\s
                • ● • ● ●\s
                • • • ● ●\s
                ● ● ● ● ●\s

                • • • • ●\s
                • • ● • ●\s
                • ● ● • •\s
                • • • ● ●\s
                ● ● ● ● ●\s

                • • • • ●\s
                • • ● • ●\s
                • • • ● •\s
                • • • ● ●\s
                ● ● ● ● ●\s

                • • • • •\s
                • • ● • •\s
                • • • ● ●\s
                • • • ● ●\s
                ● ● ● ● ●\s

                • • • • •\s
                • • ● • •\s
                • • ● • •\s
                • • • ● ●\s
                ● ● ● ● ●\s

                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • ● ● ●\s
                ● ● ● ● ●\s

                • • • • •\s
                • • • • •\s
                • • ● • •\s
                • • • ● ●\s
                ● ● • ● ●\s

                • • • • •\s
                • • • • •\s
                • • ● • •\s
                • • ● • •\s
                ● ● • ● ●\s

                • • • • •\s
                • • • • •\s
                • • ● • •\s
                • • ● • •\s
                • • ● ● ●\s

                • • • • •\s
                • • • • •\s
                • • ● • •\s
                • • ● • •\s
                • ● • • ●\s

                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • • • •\s
                • ● ● • ●\s

                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • • ● ●\s

                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • • • •\s
                • • ● • •\s

                """;
        Board board = new QuadraticBoardSizeFive(new BitManipulator());
        SolutionStrategy strategy = new DepthFirstStrategy();
        long startPosition = 0B11111_11111_11111_11011_11111L;

        Solution solution = strategy.solve(board, startPosition);

        assertThat(solution.toString(board)).isEqualTo(expectedSolution);
    }

    @Test
    public void testDepthFirstStrategyForEnglishBoard() {
        String expectedSolution = """

                    ● ● ●    \s
                    ● ● ●    \s
                ● ● ● ● ● ● ●\s
                ● ● ● • ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● ● ●    \s
                    ● • ●    \s
                ● ● ● • ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● ● ●    \s
                    ● • ●    \s
                ● • • ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • ● ●    \s
                    • • ●    \s
                ● • ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • •    \s
                    • • ●    \s
                ● • ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • •    \s
                    • • ●    \s
                ● ● • • ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • •    \s
                    • • ●    \s
                • • ● • ● ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • ●    \s
                    • • •    \s
                • • ● • • ● ●\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • ●    \s
                    • • •    \s
                • • ● • ● • •\s
                ● ● ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    ● • ●    \s
                    ● • •    \s
                • • • • ● • •\s
                ● ● • ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • ●    \s
                    • • •    \s
                • • ● • ● • •\s
                ● ● • ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • ●    \s
                    • • •    \s
                • • ● • ● • •\s
                • • ● ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • ●    \s
                    ● • •    \s
                • • • • ● • •\s
                • • • ● ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • ●    \s
                    ● • ●    \s
                • • • • • • •\s
                • • • ● • ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • •    \s
                • • • • ● • •\s
                • • • ● • ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • •    \s
                • • • • ● • •\s
                • • • ● ● • •\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • ●    \s
                • • • • • • •\s
                • • • ● • • •\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • ●    \s
                • • • • • • •\s
                • • ● ● • • •\s
                ● ● • ● ● ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • ●    \s
                • • • • • • •\s
                • • ● ● • • •\s
                • • ● ● ● ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    ● • ●    \s
                • • ● • • • •\s
                • • • ● • • •\s
                • • • ● ● ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • ●    \s
                • • • • • • •\s
                • • ● ● • • •\s
                • • • ● ● ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • ●    \s
                • • • • • • •\s
                • • • • ● • •\s
                • • • ● ● ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • ●    \s
                • • • • ● • •\s
                • • • • • • •\s
                • • • ● • ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • ● • •\s
                • • • ● • ● ●\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • ● • •\s
                • • • ● ● • •\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • ● • •\s
                • • • • • ● •\s
                    • ● ●    \s
                    ● ● ●    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • ● • •\s
                • • • • ● ● •\s
                    • ● •    \s
                    ● ● •    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • • • •\s
                • • • • • ● •\s
                    • ● ●    \s
                    ● ● •    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • • • •\s
                • • • • • ● •\s
                    • ● ●    \s
                    • • ●    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • • • •\s
                • • • • ● ● •\s
                    • ● •    \s
                    • • •    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • • • • •\s
                • • • ● • • •\s
                    • ● •    \s
                    • • •    \s

                    • • •    \s
                    • • •    \s
                • • • • • • •\s
                • • • ● • • •\s
                • • • • • • •\s
                    • • •    \s
                    • • •    \s

                """;
        Board board = new EnglishBoard(new BitManipulator());
        SolutionStrategy strategy = new DepthFirstStrategy();
        Long startPosition = board.getStartPosition();

        Solution solution = strategy.solve(board, startPosition);

        assertThat(solution.toString(board)).isEqualTo(expectedSolution);
    }
}
