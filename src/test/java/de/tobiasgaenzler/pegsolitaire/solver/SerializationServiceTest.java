package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SerializationServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(SerializationServiceTest.class);
    private static final int COUNT = 1000;

    @Test
    public void testWritingAndReadingPositions() {
        Board board = spy(Board.class);
        when(board.getName()).thenReturn("test-board");

        logger.debug("Create set of {} positions", COUNT);
        Set<Long> numbers = IntStream.range(0, COUNT).mapToLong(i -> (long) i).boxed().collect(Collectors.toSet());
        SerializationService serializationService = new SerializationService();

        Path path = serializationService.storePositionsInBinaryFile(board, numbers, 1);

        Set<Long> numbersFromFile = serializationService.readPositionsFromBinaryFile(path);

        assertThat(numbersFromFile).isEqualTo(numbers);
    }

    @Test
    public void testConvertBinaryFileToTxtFile() {
        Board board = spy(Board.class);
        when(board.getName()).thenReturn("test-board");

        logger.debug("Create set of {} positions", COUNT);
        Set<Long> numbers = IntStream.range(0, COUNT).mapToLong(i -> (long) i).boxed().collect(Collectors.toSet());
        SerializationService serializationService = new SerializationService();

        Path binaryFilePath = serializationService.storePositionsInBinaryFile(board, numbers, 1);

        Path txtFilePath = serializationService.convertBinaryFileToTxtFile(binaryFilePath);

        Set<Long> numbersFromFile = serializationService.readPositionsFromTxtFile(txtFilePath);
        assertThat(numbersFromFile).isEqualTo(numbers);
    }
}