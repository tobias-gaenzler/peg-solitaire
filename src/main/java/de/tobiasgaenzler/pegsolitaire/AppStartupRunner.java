package de.tobiasgaenzler.pegsolitaire;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.BoardFactory;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.SingleSolutionStrategy;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.SolutionStrategy;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.StrategyFactory;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.WinningPositionsStrategy;
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
@Profile("!test") // disabled for tests
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);
    public static final String SINGLE_SOLUTION_STRATEGY = "singleSolution";

    private final BoardFactory boardFactory;
    private final StrategyFactory strategyFactory;

    @Autowired
    public AppStartupRunner(BoardFactory boardFactory, StrategyFactory strategyFactory) {
        this.boardFactory = boardFactory;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public void run(ApplicationArguments args) {
        String boardName = EnglishBoard.NAME;
        String strategyName = SINGLE_SOLUTION_STRATEGY;
        if (args.containsOption("board")) {
            boardName = args.getOptionValues("board").get(0);
        }
        if (args.containsOption("strategy")) {
            strategyName = args.getOptionValues("strategy").get(0);
        }

        logger.info("Use {}", boardName);
        logger.info("Use {} strategy", strategyName);
        Board board = boardFactory.getBoard(boardName);
        SolutionStrategy<?> strategy = strategyFactory.create(strategyName);
        if (strategy instanceof SingleSolutionStrategy) {
            Solution solution = ((SingleSolutionStrategy) strategy).solve(board, board.getStartPosition());
            logger.info(solution.toString(board));
        } else if (strategy instanceof WinningPositionsStrategy) {
            List<Path> filePaths = ((WinningPositionsStrategy) strategy).solve(board, board.getStartPosition());
            logger.info("Wrote solution to the following files: {}", filePaths);
        } else {
            logger.info("Unknown strategy {}. Exiting.", strategyName);
        }
    }
}