package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.config.GlueProperties;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class LifeCalculator extends SwingWorker<Cell[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final Cell[][] board;
    private final BoardTextArea boardTextArea;
    private final GlueProperties properties;
    private final int rows;
    private final int columns;
    private final ExecutorService executor;

    protected LifeCalculator(
            LifeBoard lifeBoard,
            BoardTextArea boardTextArea,
            GlueProperties properties,
            ExecutorService executor
    ) {
        this.board = lifeBoard.getBoard();
        this.properties = properties;
        rows = properties.board().rows();
        columns = properties.board().columns();
        this.boardTextArea = boardTextArea;
        this.executor = executor;
    }

    @PreDestroy
    public void destroy() {
        executor.shutdownNow();
    }

    @Override
    protected Cell[][] doInBackground() throws InterruptedException {
        // TODO - lock/synchronise field access?

        List<CompletableFuture<Object>> futures = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int k = i;
                int m = j;

                // strategy 1: calculate all cells in a single thread
//                updateFutureCellStatus(k, m, countLiveNeighbours(k, m));

                // strategy 2: calculate every cell in a separate thread from thread pool
                futures.add(CompletableFuture.supplyAsync(() -> {
                    updateFutureCellStatus(k, m, countLiveNeighbours(k, m));

                    // If we have a long calculation here (sleep) and use cached thread pool, the program fails with
                    // [6.389s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
                    // [6.389s][warning][os,thread] Failed to start the native thread for java.lang.Thread "pool-2-thread-4044"
                    // java.util.concurrent.ExecutionException: java.lang.OutOfMemoryError: unable to create native thread
                    // BUT, it's fine with virtual thread
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    return null;
                }, executor));
            }
        }

//        LOG.info("largest pool size: {}", ((ThreadPoolExecutor) executor).getLargestPoolSize());
//        LOG.info("largest pool size: {}", ((ThreadContainer) executor).getLargestPoolSize());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        for (Cell[] cellRows : board) {
            for (Cell cell : cellRows) {
                cell.updateCurrentAliveStatus();
            }
        }

        Thread.sleep(properties.calculation().delay());

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
         * If the cell is dead, then it springs to life only in the case that it has 3 live neighbors
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
