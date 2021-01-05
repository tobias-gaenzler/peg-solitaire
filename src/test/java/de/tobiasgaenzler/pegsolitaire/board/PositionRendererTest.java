package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.tobiasgaenzler.pegsolitaire.board.PositionContent.HOLE;
import static de.tobiasgaenzler.pegsolitaire.board.PositionContent.PEG;
import static org.assertj.core.api.Assertions.assertThat;

public class PositionRendererTest {

    private final PositionRenderer positionRenderer = new PositionRenderer(new PositionTransformer());

    @Test
    public void testRenderToStringShouldRenderPositionOn4x4BoardCorrectly() {
        QuadraticBoardSizeFour quadraticBoardSizeFour = new QuadraticBoardSizeFour(new BitManipulator());
        String boardPresentation = positionRenderer.renderToString(0B1001_1110_0111_1010L, quadraticBoardSizeFour);
        assertThat(boardPresentation).isEqualTo("""

                ● • • ●\s
                ● ● ● •\s
                • ● ● ●\s
                ● • ● •\s
                """);
    }

    @Test
    public void testRenderToStringShouldRenderPositionOnEnglishBoardCorrectly() {
        EnglishBoard englishBoard = new EnglishBoard(new BitManipulator());
        String boardPresentation = positionRenderer.renderToString(0B0011100_0011100_1111111_1110111_1111111_0011100_0011100L, englishBoard);
        assertThat(boardPresentation).isEqualTo("""

                    ● ● ●    \s
                    ● ● ●    \s
                ● ● ● ● ● ● ●\s
                ● ● ● • ● ● ●\s
                ● ● ● ● ● ● ●\s
                    ● ● ●    \s
                    ● ● ●    \s
                """);
    }

    @Test
    public void testRenderToMatrixShouldRenderPositionOn4x4BoardCorrectlyForPresentation() {
        QuadraticBoardSizeFour quadraticBoardSizeFour = new QuadraticBoardSizeFour(new BitManipulator());
        List<List<PositionContent>> matrix = positionRenderer.renderToMatrix(0B1001_1110_0111_1010L, quadraticBoardSizeFour);
        // check that every position in the matrix is filled correctly
        // ● • • ●
        // ● ● ● •
        // • ● ● ●
        // ● • ● •

        // first row
        assertThat(matrix.get(0)).isEqualTo(List.of(PEG, HOLE, HOLE, PEG));
        // second row
        assertThat(matrix.get(1)).isEqualTo(List.of(PEG, PEG, PEG, HOLE));
        // third row
        assertThat(matrix.get(2)).isEqualTo(List.of(HOLE, PEG, PEG, PEG));
        // fourth row
        assertThat(matrix.get(3)).isEqualTo(List.of(PEG, HOLE, PEG, HOLE));
    }
}
