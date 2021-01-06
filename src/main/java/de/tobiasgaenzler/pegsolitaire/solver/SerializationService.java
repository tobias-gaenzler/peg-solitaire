package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SerializationService {
    private final static Logger logger = LoggerFactory.getLogger(SerializationService.class);

    public Path storePositionsInFile(Board board, Set<Long> positions, int numberOfRemainingPieces) {
        Path path = Paths.get(board.getName() + "_" + numberOfRemainingPieces + "_positions.txt");
        logger.debug("Storing positions for {} pegs in file '{}'", numberOfRemainingPieces, path);
        Instant start = Instant.now();
        String positionString = positions.stream().filter(Objects::nonNull)// ignore null values
                .map(String::valueOf)//
                .collect(Collectors.joining(","));

        try {
            Files.write(path, Collections.singletonList(positionString), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Could not write to file {}:  {}",path, e.getMessage());
            throw new RuntimeException(e);
        }
        logger.debug("Storing positions took {} ms", Duration.between(start, Instant.now()).toMillis());
        return path;
    }

    public Set<Long> readPositionsFromFile(Path path) {
        logger.debug("Reading positions from file {}", path);
        Instant start = Instant.now();
        Stream<String> lines;
        try {
            lines = Files.lines(path);
        } catch (IOException e) {
            logger.error("Could not read from file {}:  {}",path, e.getMessage());
            throw new RuntimeException(e);
        }
        String data = lines.collect(Collectors.joining(""));
        lines.close();
        Set<Long> positions = Arrays.stream(data.split(",")).mapToLong(Long::valueOf).boxed().collect(Collectors.toSet());
        logger.debug("Reading positions took {} ms", Duration.between(start, Instant.now()).toMillis());
        return positions;
    }
}
