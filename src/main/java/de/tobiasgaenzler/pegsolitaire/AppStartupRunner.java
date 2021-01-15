package de.tobiasgaenzler.pegsolitaire;

import de.tobiasgaenzler.pegsolitaire.board.Board;
import de.tobiasgaenzler.pegsolitaire.board.BoardFactory;
import de.tobiasgaenzler.pegsolitaire.board.EnglishBoard;
import de.tobiasgaenzler.pegsolitaire.solver.Solution;
import de.tobiasgaenzler.pegsolitaire.solver.strategy.*;
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
    private final StrategyFactoryProvider strategyFactoryProvider;

    @Autowired
    public AppStartupRunner(BoardFactory boardFactory, StrategyFactoryProvider strategyFactoryProvider) {
        this.boardFactory = boardFactory;
        this.strategyFactoryProvider = strategyFactoryProvider;
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

        StrategyFactory<SingleSolutionStrategy> singleSolutionStrategyFactory = strategyFactoryProvider.getFactory(SingleSolutionStrategyFactory.NAME);
        StrategyFactory<WinningPositionsStrategy> winningPositionsStrategyFactory = strategyFactoryProvider.getFactory(WinningPositionsStrategyFactory.NAME);
        if (singleSolutionStrategyFactory.getStrategyNames().contains(strategyName)) {
            SingleSolutionStrategy strategy = singleSolutionStrategyFactory.create(strategyName);
            Solution solution = strategy.solve(board, board.getStartPosition());
            logger.info(solution.toString(board));
        } else if (winningPositionsStrategyFactory.getStrategyNames().contains(strategyName)) {
            WinningPositionsStrategy strategy = winningPositionsStrategyFactory.create(strategyName);
            List<Path> filePaths = strategy.solve(board, board.getStartPosition());
            logger.info("Wrote solution to the following files: {}", filePaths);
        } else {
            logger.info("Unknown strategy {}. Exiting.", strategyName);
        }
    }
}