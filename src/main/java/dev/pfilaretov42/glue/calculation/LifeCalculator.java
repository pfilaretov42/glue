package dev.pfilaretov42.glue.calculation;

import dev.pfilaretov42.glue.awt.BoardTextArea;
import dev.pfilaretov42.glue.model.Cell;
import dev.pfilaretov42.glue.model.LifeBoard;
import dev.pfilaretov42.glue.config.GlueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class LifeCalculator extends SwingWorker<Cell[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final Cell[][] board;
    private final BoardTextArea boardTextArea;
    private final GlueProperties properties;
    private final int rows;
    private final int columns;
    private final CalculationStrategy calculationStrategy;

    protected LifeCalculator(
            LifeBoard lifeBoard,
            BoardTextArea boardTextArea,
            GlueProperties properties,
            CalculationStrategy calculationStrategy) {
        this.board = lifeBoard.getBoard();
        this.properties = properties;
        rows = properties.board().rows();
        columns = properties.board().columns();
        this.boardTextArea = boardTextArea;
        this.calculationStrategy = calculationStrategy;
    }

    @Override
    protected Cell[][] doInBackground() throws InterruptedException {
        // TODO - lock/synchronise field access?

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int k = i;
                int m = j;

                futures.add(
                        calculationStrategy.calculateCell(() -> {
                            updateFutureCellStatus(k, m, countLiveNeighbours(k, m));

                            // If we have a long calculation here (sleep) and use cached thread pool, the program fails with
                            // [6.389s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
                            // [6.389s][warning][os,thread] Failed to start the native thread for java.lang.Thread "pool-2-thread-4044"
                            // java.util.concurrent.ExecutionException: java.lang.OutOfMemoryError: unable to create native thread
                            // BUT, it's fine with virtual thread
                            try {
                                Thread.sleep(properties.calculation().cellDelay());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        })
                );
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        for (Cell[] cellRows : board) {
            for (Cell cell : cellRows) {
                cell.updateCurrentAliveStatus();
            }
        }

        Thread.sleep(properties.calculation().boardDelay());

        return board;
    }

    @Override
    protected void done() {
        try {
            boardTextArea.updateView(get());
            getContinueButton().doClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFutureCellStatus(int i, int j, int liveNeighboursCount) {
        /*
         * If the cell is alive, then it stays alive if it has either 2 or 3 live neighbors
         * If the cell is dead, then it springs to life only if it has exactly 3 live neighbors
         */
        if (board[i][j].isCurrentlyAlive()) {
            if (liveNeighboursCount != 2 && liveNeighboursCount != 3) {
                // becomes dead
                board[i][j].setFutureAlive(false);
            }
        } else {
            if (liveNeighboursCount == 3) {
                // becomes alive
                board[i][j].setFutureAlive(true);
            }
        }
    }

    protected abstract JButton getContinueButton();

    private int countLiveNeighbours(int i, int j) {
        int liveNeighboursCount = 0;
        if (i > 0) {
            if (j > 0 && board[i - 1][j - 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
            if (board[i - 1][j].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
            if (j < columns - 1 && board[i - 1][j + 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
        }
        if (j > 0) {
            if (board[i][j - 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
            if (i < rows - 1 && board[i + 1][j - 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
        }
        if (j < columns - 1 && board[i][j + 1].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        if (i < rows - 1 && board[i + 1][j].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        if (i < rows - 1 && j < columns - 1 && board[i + 1][j + 1].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        return liveNeighboursCount;
    }
}
