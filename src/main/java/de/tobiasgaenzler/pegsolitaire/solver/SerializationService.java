package de.tobiasgaenzler.pegsolitaire.solver;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SerializationService {
    private final static Logger logger = LoggerFactory.getLogger(SerializationService.class);
    public static final String POSITION_SEPARATOR = ",";

    /**
     * Write set of positions to file. Binary file format is used because it is faster than using strings
     *
     * @param board                   only used for filename
     * @param positions               set of positions to write to file as binary data
     * @param numberOfRemainingPieces only used for filename
     * @return the file path of the file containing the positions
     */

    public Path storePositionsInBinaryFile(Board board, Set<Long> positions, int numberOfRemainingPieces) {
        Path positionsFilePath = getBinaryPositionFilePath(board, numberOfRemainingPieces);
        logger.debug("Storing positions for {} pegs in file '{}'", numberOfRemainingPieces, positionsFilePath);
        Instant start = Instant.now();
        try (FileOutputStream fileOutputStream = new FileOutputStream(positionsFilePath.getFileName().toString());
             DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream))
        ) {
            positions.forEach(position -> {
                try {
                    outStream.writeLong(position);
                } catch (IOException e) {
                    logger.error("Could not write to file {}:  {}", positionsFilePath, e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            logger.error("Could not open file '{}' for writing:  {}", positionsFilePath, e.getMessage());
            throw new RuntimeException(e);
        }
        logger.debug("Storing positions took {} ms", Duration.between(start, Instant.now()).toMillis());
        return positionsFilePath;
    }

    @SuppressWarnings("InfiniteLoopStatement") // for performance reasons we use an infinite loop here
    public Set<Long> readPositionsFromBinaryFile(Path path) {
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

    /**
     * Read binary position file and write the positions (long) as string to txt file.
     * Use streams to avoid that all source file content is loaded into memory.
     * Source file is deleted after completion.
     * Useful for further processing of calculated results e.g. in an app.
     *
     * @param binaryFilePath path to the binary positions file
     * @return path to the txt file
     */
    @SuppressWarnings("InfiniteLoopStatement") // for performance reasons we use an infinite loop here
    public Path convertBinaryFileToTxtFile(Path binaryFilePath) {
        logger.debug("Reading positions from file {}", binaryFilePath);
        Instant start = Instant.now();

        Path txtFilePath = convertToTxtPositionFilePath(binaryFilePath);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(binaryFilePath.toFile()));
             DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(txtFilePath.toString()))
        ) {
            while (true) { // dataInputStream.available() is too slow
                Long position = dataInputStream.readLong();
                bufferedOutputStream.write((position + POSITION_SEPARATOR).getBytes());
            }
        } catch (EOFException e) {
            logger.debug("reached end of file {}", binaryFilePath);
        } catch (IOException e) {
            logger.error("Could not read from file {}:  {}", binaryFilePath, e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            Files.delete(binaryFilePath);
        } catch (IOException e) {
            logger.error("Could not delete file {}:  {}", binaryFilePath, e.getMessage());
        }

        logger.debug("Converting binary to txt file took {} ms", Duration.between(start, Instant.now()).toMillis());
        return txtFilePath;
    }

    public Set<Long> readPositionsFromTxtFile(Path txtFilePath) {
        Set<Long> numbersFromFile;
        try {
            String numbersFromFileString = Files.readString(txtFilePath);
            numbersFromFile = Arrays.stream(numbersFromFileString.split(SerializationService.POSITION_SEPARATOR))//
                    .map(Long::valueOf)//
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            logger.error("Could not open or write to file '{}':  {}", txtFilePath, e.getMessage());
            throw new RuntimeException(e);
        }
        return numbersFromFile;
    }

    private Path getBinaryPositionFilePath(Board board, int numberOfRemainingPieces) {
        return Paths.get(board.getName() + "_" + numberOfRemainingPieces + "_positions.bin");
    }

    private Path convertToTxtPositionFilePath(Path binaryFilePath) {
        // replace ".bin" with ".txt"
        String txtFilePath = binaryFilePath.toString().replace(".bin", ".txt");
        return Path.of(txtFilePath);
    }

}
