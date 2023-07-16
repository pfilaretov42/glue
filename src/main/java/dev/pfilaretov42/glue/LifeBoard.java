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
    private final LifeButton[][] field = new LifeButton[ROWS][COLUMNS];

    public LifeBoard(LifeActionListener lifeActionListener) {
        this.lifeActionListener = lifeActionListener;
    }

    public void init(JPanel panel) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                field[i][j] = new LifeButton();
                panel.add(field[i][j]);

                field[i][j].setFocusable(false);
                // this is how background is set:
                field[i][j].setOpaque(true);
                field[i][j].setBorderPainted(false);

                // initial state of the field
                boolean isAlive = i == j && i > 2 && i < 7;
                field[i][j].setAlive(isAlive);
                field[i][j].setBackground(isAlive ? COLOR_LIFE : COLOR_NO_LIFE);
                field[i][j].addActionListener(lifeActionListener);
            }
        }
    }

    @SuppressWarnings("java:S2189")
    public void run() {

        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            LOG.info("submitting...");
//                executor.submit(() -> );
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    new LifeCalculator(field[i][j]).execute();
                }
            }

            LOG.info("Execution submitted");
        }

    }
}
