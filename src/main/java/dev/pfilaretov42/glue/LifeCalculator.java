package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static dev.pfilaretov42.glue.GlueApplication.*;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class LifeCalculator extends SwingWorker<Color[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final LifeButton[][] board;
    private final StartButton startButton;

    public LifeCalculator(LifeBoard lifeBoard, StartButton startButton) {
        this.board = lifeBoard.getBoard();
        this.startButton = startButton;
    }

    @Override
    protected Color[][] doInBackground() {
        // TODO - lock/synchronise field access?

        Color[][] colors = new Color[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                /*
                 * If the cell is alive, then it stays alive if it has either 2 or 3 live neighbors
                 * If the cell is dead, then it springs to life only in the case that it has 3 live neighbors
                 */

                int liveNeighboursCount = countLiveNeighbours(i, j);
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

                colors[i][j] = board[i][j].isFutureAlive() ? COLOR_LIFE : COLOR_NO_LIFE;
            }
        }

        for (LifeButton[] buttons : board) {
            for (LifeButton button : buttons) {
                button.updateCurrentAliveStatus();
            }
        }

        return colors;
    }

    @Override
    protected void done() {
        try {
            // TODO - move set background to LifeButton class

            Color[][] colors = get();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    board[i][j].setBackground(colors[i][j]);
                }
            }

            startButton.doClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
