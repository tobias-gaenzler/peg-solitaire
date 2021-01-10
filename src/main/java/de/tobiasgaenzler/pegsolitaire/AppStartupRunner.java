package de.tobiasgaenzler.pegsolitaire;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.BoardFactory;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.DepthFirstStrategy;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.ParallelStreamStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
@Profile("!test")
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

	private final DepthFirstStrategy depthFirstStrategy;
	private final ParallelStreamStrategy parallelStreamStrategy;
	private final BoardFactory boardFactory;

    @Autowired
    public AppStartupRunner(DepthFirstStrategy depthFirstStrategy, ParallelStreamStrategy parallelStreamStrategy, BoardFactory boardFactory) {
        this.depthFirstStrategy = depthFirstStrategy;
        this.parallelStreamStrategy = parallelStreamStrategy;
        this.boardFactory = boardFactory;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Application started with option names : {}", args.getOptionNames());
        String boardName = EnglishBoard.NAME;
        String strategyName = "depthFirst";
        if(args.containsOption("board")) {
            boardName = args.getOptionValues("board").get(0);
        }
        if(args.containsOption("strategy")) {
            strategyName = args.getOptionValues("strategy").get(0);
        }

        logger.info("Use {}", boardName);
		Board board = boardFactory.getBoard(boardName);
		if(strategyName.equals("depthFirst")) {
            logger.info("Use {} strategy", strategyName);
            Solution solution = depthFirstStrategy.solve(board, board.getStartPosition());
            logger.info(solution.toString(board));
        } else if(strategyName.equals("parallel")) {
            logger.info("Use {} strategy", strategyName);
            List<Path> filePaths = parallelStreamStrategy.solve(board, board.getStartPosition());
            logger.info("Wrote solution to the following files: {}", filePaths);
        } else {
            logger.info("Unknown strategy {}. Exiting.", strategyName);
        }
    }
}