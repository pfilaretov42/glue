package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static dev.pfilaretov42.glue.GlueApplication.*;

public abstract class LifeCalculator extends SwingWorker<Cell[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final Cell[][] board;
    private final BoardTextArea boardTextArea;

    protected LifeCalculator(LifeBoard lifeBoard, BoardTextArea boardTextArea) {
        this.board = lifeBoard.getBoard();
        this.boardTextArea = boardTextArea;
    }

    @Override
    protected Cell[][] doInBackground() throws InterruptedException {
        // TODO - lock/synchronise field access?

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int liveNeighboursCount = countLiveNeighbours(i, j);
                updateFutureCellStatus(i, j, liveNeighboursCount);
            }
        }

        for (Cell[] rows : board) {
            for (Cell cell : rows) {
                cell.updateCurrentAliveStatus();
            }
        }

        // TODO - move value to properties - initial speed
        Thread.sleep(0);

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
            if (j < COLUMNS - 1 && board[i - 1][j + 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
        }
        if (j > 0) {
            if (board[i][j - 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
            if (i < ROWS - 1 && board[i + 1][j - 1].isCurrentlyAlive()) {
                liveNeighboursCount++;
            }
        }
        if (j < COLUMNS - 1 && board[i][j + 1].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        if (i < ROWS - 1 && board[i + 1][j].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        if (i < ROWS - 1 && j < COLUMNS - 1 && board[i + 1][j + 1].isCurrentlyAlive()) {
            liveNeighboursCount++;
        }
        return liveNeighboursCount;
    }
}
