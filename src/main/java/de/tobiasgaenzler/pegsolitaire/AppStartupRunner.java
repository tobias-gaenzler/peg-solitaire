package de.tobiasgaenzler.pegsolitaire;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.BoardFactory;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.DepthFirstStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);
    @Autowired
	private DepthFirstStrategy strategy;
    @Autowired
	private BoardFactory boardFactory;

    @Override
    public void run(ApplicationArguments args) {
        LOG.info("Application started with option names : {}",
                args.getOptionNames());
        LOG.info("Calculating a winning position for english board");
		Board englishBoard = boardFactory.getBoard(EnglishBoard.NAME);
		strategy.solve(englishBoard, englishBoard.getStartPosition());
    }
}