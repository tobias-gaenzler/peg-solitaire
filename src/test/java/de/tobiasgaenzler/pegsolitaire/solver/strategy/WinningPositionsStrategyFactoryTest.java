package de.tobiasgaenzler.pegsolitaire.solver.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // prevent that a solution is calculated in tests via the AppStartupRunner
public class WinningPositionsStrategyFactoryTest {

    private final WinningPositionsStrategyFactory factory;

    @Autowired
    public WinningPositionsStrategyFactoryTest(WinningPositionsStrategyFactory factory) {
        this.factory = factory;
    }

    @Test
    public void testSerializingStrategyCreation() {
        WinningPositionsStrategy strategy = factory.create(SerializingStrategy.NAME);
        assertThat(strategy).isInstanceOf(SerializingStrategy.class);
    }
    @Test
    public void testHighMemoryUsageStrategyCreation() {
        WinningPositionsStrategy strategy = factory.create(HighMemoryUsageStrategy.NAME);
        assertThat(strategy).isInstanceOf(HighMemoryUsageStrategy.class);
    }
}
