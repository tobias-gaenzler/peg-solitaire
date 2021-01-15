package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import java.util.Set;

public interface StrategyFactory<T> {

    T create(String strategyName);

    String getName();

    Set<String> getStrategyNames();
}