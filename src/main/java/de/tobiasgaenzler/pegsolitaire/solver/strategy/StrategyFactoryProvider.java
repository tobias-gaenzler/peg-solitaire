package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StrategyFactoryProvider {
    private final Map<String, StrategyFactory> strategyFactoryNameToStrategyFactoryMap;

    @Autowired
    private StrategyFactoryProvider(List<StrategyFactory> strategyFactories) {
        strategyFactoryNameToStrategyFactoryMap = strategyFactories.stream().collect(
                Collectors.toMap(StrategyFactory::getName, Function.identity()));
    }

    public StrategyFactory getFactory(String name) {
        StrategyFactory strategy = strategyFactoryNameToStrategyFactoryMap.get(name);
        if (strategy == null) {
            throw new RuntimeException("Unknown strategy factory name: " + name + ". Available strategy factories " +
                    String.join(",", strategyFactoryNameToStrategyFactoryMap.keySet()));
        }
        return strategy;
    }


}