package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Component
public class SerializationService {
    private final static Logger logger = LoggerFactory.getLogger(SerializationService.class);

    public Path storePositionsInFile(Board board, Set<Long> positions, int numberOfRemainingPieces) {
        Path path = Paths.get(board.getName() + "_" + numberOfRemainingPieces + "_positions.txt");
        logger.debug("Storing positions for {} pegs in file '{}'", numberOfRemainingPieces, path);
        Instant start = Instant.now();
        try (FileOutputStream fileOutputStream = new FileOutputStream(path.getFileName().toString());
             DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream))
        ) {
            positions.forEach(position -> {
                try {
                    outStream.writeLong(position);
                } catch (IOException e) {
                    logger.error("Could not write to file {}:  {}", path, e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            logger.error("Could not open file '{}' for writing:  {}", path, e.getMessage());
            throw new RuntimeException(e);
        }
        logger.debug("Storing positions took {} ms", Duration.between(start, Instant.now()).toMillis());
        return path;
    }

    @SuppressWarnings("InfiniteLoopStatement") // for performance reasons we use an infinite loop here
    public Set<Long> readPositionsFromFile(Path path) {
        logger.debug("Reading positions from file {}", path);
        Set<Long> positions = new HashSet<>();
        Instant start = Instant.now();

        try (BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(path.toFile()));
             DataInputStream reader = new DataInputStream(fileInputStream)
        ) {
            while (true) { // reader.available() is too slow
                positions.add(reader.readLong());
            }
        } catch (EOFException e) {
            logger.debug("reached end of file {}", path);
        } catch (IOException e) {
            logger.error("Could not read from file {}:  {}", path, e.getMessage());
            throw new RuntimeException(e);
        }

        logger.debug("Reading positions took {} ms", Duration.between(start, Instant.now()).toMillis());
        return positions;
    }
}
