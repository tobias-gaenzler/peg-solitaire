package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import de.tobiasgaenzler.pegsolitaire.board.Board;

public interface SolutionStrategy<T> {

    T solve(Board board, Long startPosition);

    String getName();

}
