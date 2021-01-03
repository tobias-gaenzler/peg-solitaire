package de.tobiasgaenzler.pegsolitaire.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A factory which adheres to the open/close principle (new boards can be added without changing existing code).
 * Boards are created by using the board name.
 */
@Service
public class BoardFactory {

    private final Map<String, Board> boardNameToBoardMap;

    @Autowired
    private BoardFactory(List<Board> boards) {
        boardNameToBoardMap = boards.stream().collect(Collectors.toMap(Board::getName, Function.identity()));
    }

    public Board getBoard(String name) {
        Board board = boardNameToBoardMap.get(name);
        if (board == null) {
            throw new RuntimeException("Unknown board name: " + name + ". Available boards: " +
                    String.join(",", boardNameToBoardMap.keySet()));
        }
        return board;
    }
}