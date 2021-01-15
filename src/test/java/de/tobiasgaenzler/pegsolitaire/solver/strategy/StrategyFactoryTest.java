package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // prevent that a solution is calculated in tests via the AppStartupRunner
public class StrategyFactoryTest {

    private final StrategyFactory factory;

    @Autowired
    public StrategyFactoryTest(StrategyFactory factory) {
        this.factory = factory;
    }

    @Test
    public void testSerializingStrategyCreation() {
        WinningPositionsStrategy strategy = (WinningPositionsStrategy) factory.create(SerializingStrategy.NAME);
        assertThat(strategy).isInstanceOf(SerializingStrategy.class);
    }

    @Test
    public void testHighMemoryUsageStrategyCreation() {
        WinningPositionsStrategy strategy = (WinningPositionsStrategy) factory.create(HighMemoryUsageStrategy.NAME);
        assertThat(strategy).isInstanceOf(HighMemoryUsageStrategy.class);
    }

    @Test
    public void testDepthFirstStrategyCreation() {
        SingleSolutionStrategy strategy = (SingleSolutionStrategy) factory.create(DepthFirstStrategy.NAME);
        assertThat(strategy).isInstanceOf(DepthFirstStrategy.class);
    }

}
