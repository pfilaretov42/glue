package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

                board[i][j] = new LifeButton();
                panel.add(board[i][j]);

                board[i][j].setFocusable(false);
                // this is how background is set:
                board[i][j].setOpaque(true);
                board[i][j].setBorderPainted(false);

                // initial state of the field
                boolean isAlive = i == j && i > 2 && i < 7;
                board[i][j].setAlive(isAlive);
                board[i][j].setBackground(isAlive ? COLOR_LIFE : COLOR_NO_LIFE);
                board[i][j].addActionListener(lifeActionListener);
            }
        }
    }

    public void run() {

        try (ExecutorService executor = Executors.newCachedThreadPool()) {
//                executor.submit(() -> );
            LOG.info("submitting...");
            LifeCalculator lifeCalculator = new LifeCalculator(board);
            lifeCalculator.execute();

            LOG.info("Execution submitted");
        }

    }
}
