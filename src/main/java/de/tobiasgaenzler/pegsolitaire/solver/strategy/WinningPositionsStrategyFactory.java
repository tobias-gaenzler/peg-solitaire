package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A factory for creating single solution strategies which adheres to the open/close principle (new strategies can be added without changing existing code).
 * Strategies are created by using the strategy name.
 */
@Service
public class WinningPositionsStrategyFactory implements StrategyFactory<WinningPositionsStrategy> {

    public static final String NAME = "winningPositions";
    private final Map<String, WinningPositionsStrategy> strategyNameToStrategyMap;

    @Autowired
    private WinningPositionsStrategyFactory(List<WinningPositionsStrategy> winningPositionsStrategies) {
        strategyNameToStrategyMap = winningPositionsStrategies.stream().collect(Collectors.toMap(WinningPositionsStrategy::getName, Function.identity()));
    }

    @Override
    public WinningPositionsStrategy create(String name) {
        WinningPositionsStrategy strategy = strategyNameToStrategyMap.get(name);
        if (strategy == null) {
            throw new RuntimeException("Unknown strategy name: " + name + ". Available strategies " +
                    String.join(",", strategyNameToStrategyMap.keySet()));
        }
        return strategy;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Set<String> getStrategyNames() {
        return strategyNameToStrategyMap.keySet();
    }

}
