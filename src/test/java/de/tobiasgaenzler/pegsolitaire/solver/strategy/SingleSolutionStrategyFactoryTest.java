package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // prevent that a solution is calculated in tests via the AppStartupRunner
public class SingleSolutionStrategyFactoryTest {

    private final SingleSolutionStrategyFactory factory;

    @Autowired
    public SingleSolutionStrategyFactoryTest(SingleSolutionStrategyFactory factory) {
        this.factory = factory;
    }

    @Test
    public void testDepthFirstStrategyCreation() {
        SingleSolutionStrategy strategy = factory.create(DepthFirstStrategy.NAME);
        assertThat(strategy).isInstanceOf(DepthFirstStrategy.class);
    }
}
