package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.config.GlueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public abstract class LifeCalculator extends SwingWorker<Cell[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final Cell[][] board;
    private final BoardTextArea boardTextArea;
    private final GlueProperties properties;
    private final int rows;
    private final int columns;

    protected LifeCalculator(LifeBoard lifeBoard, BoardTextArea boardTextArea, GlueProperties properties) {
        this.board = lifeBoard.getBoard();
        this.properties = properties;
        rows = properties.board().rows();
        columns = properties.board().columns();
        this.boardTextArea = boardTextArea;
    }

    @Override
    protected Cell[][] doInBackground() throws InterruptedException {
        // TODO - lock/synchronise field access?

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int liveNeighboursCount = countLiveNeighbours(i, j);
                updateFutureCellStatus(i, j, liveNeighboursCount);
            }
        }

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
