package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SerializationServiceTest {
    private static final int COUNT = 100;

    @Test
    public void testWritingAndReadingPositionsInFile() {
        Board board = spy(Board.class);
        when(board.getName()).thenReturn("test-board");
        Set<Long> numbers =
                IntStream.range(0, COUNT).mapToLong(i -> (long) i).boxed().collect(Collectors.toSet());
        SerializationService serializationService = new SerializationService();
        Path path = serializationService.storePositionsInFile(board, numbers, 1);

        Set<Long> numbersRead = serializationService.readPositionsFromFile(path);
        assertThat(numbersRead).isEqualTo(numbers);
    }
}