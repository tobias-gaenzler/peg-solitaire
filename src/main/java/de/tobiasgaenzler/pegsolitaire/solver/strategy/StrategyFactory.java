package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory used to create either *WinningPositionsStrategies* or *SingleSolutionStrategies*.
 */
@Service
public class StrategyFactory {
    private final Map<String, SingleSolutionStrategy> singleSolutionStrategyMap;
    private final Map<String, WinningPositionsStrategy> winningPositionsStrategyMap;

    @Autowired
    private StrategyFactory(List<SingleSolutionStrategy> singleSolutionStrategies, List<WinningPositionsStrategy> winningPositionsStrategy) {
        singleSolutionStrategyMap = singleSolutionStrategies.stream().collect(Collectors.toMap(SingleSolutionStrategy::getName, Function.identity()));
        winningPositionsStrategyMap = winningPositionsStrategy.stream().collect(Collectors.toMap(WinningPositionsStrategy::getName, Function.identity()));
    }

    public SolutionStrategy<?> create(String name) {
        if (singleSolutionStrategyMap.containsKey(name)) {
            return singleSolutionStrategyMap.get(name);
        } else if (winningPositionsStrategyMap.containsKey(name)) {
            return winningPositionsStrategyMap.get(name);
        } else {
            throw new RuntimeException("Unknown strategy name: " + name + ". Available strategies " +
                    String.join(",", singleSolutionStrategyMap.keySet()) + "," + String.join(",", winningPositionsStrategyMap.keySet()));
        }
    }
}