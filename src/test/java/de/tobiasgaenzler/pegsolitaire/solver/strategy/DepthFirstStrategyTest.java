package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.BoardFactory;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepthFirstStrategyTest {

    @Test
    public void testDepthFirstStrategyForQuadraticBoard() {
        // depth first is deterministic and always returns the same solution.
        // prefer visual representation (better documentation and easier to grasp if really a solution).
        String expectedSolution = """
                
                ⚫ ⚫ ⚫ ⚪\s
                ⚫ ⚪ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚫ ⚪ ⚪ ⚫\s
                ⚫ ⚪ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚫ ⚪ ⚪ ⚫\s
                ⚫ ⚫ ⚪ ⚪\s
                ⚫ ⚫ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚫ ⚪ ⚪ ⚫\s
                ⚪ ⚪ ⚫ ⚪\s
                ⚫ ⚫ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚫ ⚪ ⚫ ⚫\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚫ ⚫ ⚪ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚫ ⚫ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚫ ⚫ ⚪ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚪ ⚪ ⚫ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚫ ⚫ ⚪ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚪ ⚪ ⚫ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚫ ⚫\s
                ⚫ ⚫ ⚫ ⚫\s

                ⚪ ⚪ ⚫ ⚪\s
                ⚪ ⚪ ⚫ ⚪\s
                ⚪ ⚪ ⚪ ⚫\s
                ⚫ ⚫ ⚪ ⚫\s

                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚫ ⚫\s
                ⚫ ⚫ ⚪ ⚫\s

                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚫ ⚪ ⚪\s
                ⚫ ⚫ ⚪ ⚫\s

                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚫ ⚪ ⚪\s
                ⚪ ⚪ ⚫ ⚫\s

                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚫ ⚪ ⚪\s
                ⚪ ⚫ ⚪ ⚪\s

                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚫ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                ⚪ ⚪ ⚪ ⚪\s
                
                """;
        Board board = new BoardFactory().createQuadraticBoard(4);
        SolutionStrategy strategy = new DepthFirstStrategy();
        long startPosition = 0B1110_1011_1111_1111L;

        Solution solution = strategy.solve(board, startPosition);

        assertThat(solution.toString(board)).isEqualTo(expectedSolution);
    }

    @Test
    public void testDepthFirstStrategyForEnglishBoard() {
        String expectedSolution = """
                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O * O O O\s
                O O O O O O O\s
                    O O O    \s
                    O O O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O * O O O\s
                    O * O    \s
                    O O O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O O * * O\s
                    O * O    \s
                    O O O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O O O * O\s
                    O * *    \s
                    O O *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O O O * O\s
                    O * *    \s
                    * * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O * * O O\s
                    O * *    \s
                    * * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O O * O * *\s
                    O * *    \s
                    * * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                O O * * O * *\s
                    * * *    \s
                    O * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O O O\s
                * * O * O * *\s
                    * * *    \s
                    O * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O * O O\s
                * * O * * * *\s
                    * * O    \s
                    O * O    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O * O O\s
                * * O * O * *\s
                    * * *    \s
                    O * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O O * *\s
                * * O * O * *\s
                    * * *    \s
                    O * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O O O * * *\s
                * * O * * * *\s
                    * * O    \s
                    O * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O * O * * *\s
                * * * * * * *\s
                    O * O    \s
                    O * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                O O * O * * *\s
                * * O * * * *\s
                    * * O    \s
                    * * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                * * O O * * *\s
                * * O * * * *\s
                    * * O    \s
                    * * *    \s

                    O O O    \s
                    O O O    \s
                O O O O O O O\s
                * * * O * * *\s
                * * * * * * *\s
                    O * O    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O O O * O O\s
                * * * O O * *\s
                * * * * * * *\s
                    O * O    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O O O O * *\s
                * * * O O * *\s
                * * * * * * *\s
                    O * O    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O O O * * *\s
                * * * O * * *\s
                * * * * O * *\s
                    O * O    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O O O * * *\s
                * * * O O * *\s
                * * * * * * *\s
                    O * *    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O O O * * *\s
                * * O * * * *\s
                * * * * * * *\s
                    O * *    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O * O * * *\s
                * * * * * * *\s
                * * O * * * *\s
                    O * *    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                O O * O * * *\s
                * * O * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                * * O O * * *\s
                * * O * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    O O O    \s
                    O O *    \s
                * O * * * * *\s
                * * O * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    * O O    \s
                    * O *    \s
                * O O * * * *\s
                * * O * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    * O O    \s
                    O O *    \s
                * O * * * * *\s
                * * * * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    O * *    \s
                    O O *    \s
                * O * * * * *\s
                * * * * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    * * *    \s
                    * O *    \s
                * O O * * * *\s
                * * * * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    * * *    \s
                    * O *    \s
                * * * O * * *\s
                * * * * * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s

                    * * *    \s
                    * * *    \s
                * * * * * * *\s
                * * * O * * *\s
                * * * * * * *\s
                    * * *    \s
                    * * *    \s


                """;
        Board board = new EnglishBoard();
        SolutionStrategy strategy = new DepthFirstStrategy();
        Long startPosition = board.getStartPosition();

        Solution solution = strategy.solve(board, startPosition);

        assertThat(solution.toString(board)).isEqualTo(expectedSolution);
    }
}
