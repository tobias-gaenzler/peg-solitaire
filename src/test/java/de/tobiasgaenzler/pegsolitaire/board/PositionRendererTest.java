package de.tobiasgaenzler.pegsolitaire.board;

import de.tobiasgaenzler.pegsolitaire.solver.strategy.bits.BitManipulator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.tobiasgaenzler.pegsolitaire.board.PositionContent.HOLE;
import static de.tobiasgaenzler.pegsolitaire.board.PositionContent.PEG;
import static org.assertj.core.api.Assertions.assertThat;

public class PositionRendererTest {

    private final PositionRenderer testee = new PositionRenderer(new PositionTransformer());

    @Test
    public void renderToStringShouldRenderPositionOn4x4BoardCorrectly() {
        String boardPresentation = testee.renderToString(0B1001_1110_0111_1010L, new QuadraticBoardSizeFour(new BitManipulator()));
        assertThat(boardPresentation).isEqualTo("""
                                
                ⚫ ⚪ ⚪ ⚫\s
                ⚫ ⚫ ⚫ ⚪\s
                ⚪ ⚫ ⚫ ⚫\s
                ⚫ ⚪ ⚫ ⚪\s
                """);
    }

    @Test
    public void renderToMatrixShouldRenderPositionOn4x4BoardCorrectlyForPresentation() {
        List<List<PositionContent>> matrix = testee.renderToMatrix(0B1001_1110_0111_1010L, new QuadraticBoardSizeFour(new BitManipulator()));
        assertThat(matrix.get(0).get(0)).isEqualTo(PEG);
        assertThat(matrix.get(0).get(1)).isEqualTo(HOLE);
        assertThat(matrix.get(0).get(2)).isEqualTo(HOLE);
        assertThat(matrix.get(0).get(3)).isEqualTo(PEG);
        assertThat(matrix.get(1).get(0)).isEqualTo(PEG);
        assertThat(matrix.get(1).get(1)).isEqualTo(PEG);
        assertThat(matrix.get(1).get(2)).isEqualTo(PEG);
        assertThat(matrix.get(1).get(3)).isEqualTo(HOLE);
        assertThat(matrix.get(2).get(0)).isEqualTo(HOLE);
        assertThat(matrix.get(2).get(1)).isEqualTo(PEG);
        assertThat(matrix.get(2).get(2)).isEqualTo(PEG);
        assertThat(matrix.get(2).get(3)).isEqualTo(PEG);
        assertThat(matrix.get(3).get(0)).isEqualTo(PEG);
        assertThat(matrix.get(3).get(1)).isEqualTo(HOLE);
        assertThat(matrix.get(3).get(2)).isEqualTo(PEG);
        assertThat(matrix.get(3).get(3)).isEqualTo(HOLE);
    }
}
