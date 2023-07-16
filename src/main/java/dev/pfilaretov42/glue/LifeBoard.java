package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static dev.pfilaretov42.glue.GlueApplication.*;

@Component
public class LifeBoard {

    private static final Logger LOG = LoggerFactory.getLogger(LifeBoard.class);

    private final LifeActionListener lifeActionListener;
    private final LifeButton[][] board = new LifeButton[ROWS][COLUMNS];

    public LifeBoard(LifeActionListener lifeActionListener) {
        this.lifeActionListener = lifeActionListener;
    }

    public void init(JPanel panel) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                // start with glider
                int rowOffset = 5;
                int columnOffset = 5;
                boolean isAlive =
                        (i == ROWS - 1 - rowOffset && j == COLUMNS - 2 - columnOffset) ||
                                (i == ROWS - 2 - rowOffset && j == COLUMNS - 3 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 1 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 2 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 3 - columnOffset);
                board[i][j] = new LifeButton(isAlive);
                panel.add(board[i][j]);

                board[i][j].setFocusable(false);
                // this is how background is set:
                board[i][j].setOpaque(true);
                board[i][j].setBorderPainted(false);

                // initial state of the field
                board[i][j].setBackground(isAlive ? COLOR_LIFE : COLOR_NO_LIFE);
                // TODO handle board cell click
//                board[i][j].addActionListener(lifeActionListener);
            }
        }
    }

    public void run() {
        LOG.info("submitting...");
//        LifeCalculator lifeCalculator = new LifeCalculator(board, startButton);
//        lifeCalculator.execute();
    }

    public LifeButton[][] getBoard() {
        return board;
    }
}
