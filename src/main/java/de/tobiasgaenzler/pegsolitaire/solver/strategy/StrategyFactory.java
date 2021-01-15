package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import java.util.Set;

/**
 * Factory used to create either *WinningPositionsStrategies* or *SingleSolutionStrategies*.
 *
 * @param <T> type of strategy (*WinningPositionsStrategy* or *SingleSolutionStrategy*)
 */
public interface StrategyFactory<T> {

    T create(String strategyName);

    /**
     * Needed for retrieving a factory by name.
     */
    String getName();

    /**
     * Needed to check if a factory can create a strategy.
     */
    Set<String> getStrategyNames();
}