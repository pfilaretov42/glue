package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static dev.pfilaretov42.glue.GlueApplication.*;

@Component
public class LifeField {

    private static final Logger LOG = LoggerFactory.getLogger(LifeField.class);

    private final LifeActionListener lifeActionListener;
    private final JButton[][] field = new JButton[ROWS][COLUMNS];

    public LifeField(LifeActionListener lifeActionListener) {
        this.lifeActionListener = lifeActionListener;
    }

    public void init(JPanel panel) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                field[i][j] = new JButton();
                panel.add(field[i][j]);

                field[i][j].setFocusable(false);
                // this is how background is set:
                field[i][j].setOpaque(true);
                field[i][j].setBorderPainted(false);

                // initial state of the field
                if (i == j && i > 2 && i < 7) {
                    field[i][j].setBackground(COLOR_LIFE);
                } else {
                    field[i][j].setBackground(COLOR_NO_LIFE);
                }

                field[i][j].addActionListener(lifeActionListener);
            }
        }
    }

    @SuppressWarnings("java:S2189")
    public void run() {
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            executor.submit(() -> {
                // calculate everything in a single thread
                LOG.info("calculating...");
                // TODO - app hangs with while
                while (true) {
                    for (int i = 0; i < ROWS; i++) {
                        for (int j = 0; j < COLUMNS; j++) {
                            Color background = field[i][j].getBackground();
                            boolean isLife = background == COLOR_LIFE;
                            if (isLife) {
                                field[i][j].setBackground(COLOR_NO_LIFE);
                            } else {
                                field[i][j].setBackground(COLOR_LIFE);
                            }
                        }
                    }

                    Thread.sleep(5000);
                }
            });

            LOG.info("Execution submitted");
        }

    }
}
