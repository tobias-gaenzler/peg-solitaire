package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface for finding all winning positions for a solitaire game.
 * A winning position is a position from which the game can still be solved.
 * From the winning positions all solutions can be derived.
 */
public interface WinningPositionsStrategy extends SolutionStrategy<List<Path>> {
}
