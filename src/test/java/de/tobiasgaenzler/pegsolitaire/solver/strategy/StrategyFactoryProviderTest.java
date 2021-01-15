package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // prevent that a solution is calculated in tests via the AppStartupRunner
public class StrategyFactoryProviderTest {

    private final StrategyFactoryProvider strategyFactoryProvider;

    @Autowired
    public StrategyFactoryProviderTest(StrategyFactoryProvider strategyFactoryProvider) {
        this.strategyFactoryProvider = strategyFactoryProvider;
    }

    @Test
    public void testSingleSolutionFactory() {
        StrategyFactory<?> factory = strategyFactoryProvider.getFactory(SingleSolutionStrategyFactory.NAME);
        assertThat(factory).isInstanceOf(SingleSolutionStrategyFactory.class);
    }
    @Test
    public void testWinningPositionsStrategyFactory() {
        StrategyFactory<?> factory = strategyFactoryProvider.getFactory(WinningPositionsStrategyFactory.NAME);
        assertThat(factory).isInstanceOf(WinningPositionsStrategyFactory.class);
    }
}
