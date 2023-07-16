package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static dev.pfilaretov42.glue.GlueApplication.*;

public class LifeCalculator extends SwingWorker<Color[][], Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final LifeButton[][] board;

    public LifeCalculator(LifeButton[][] board) {
        this.board = board;
    }

    @Override
    protected Color[][] doInBackground() {
        LOG.info("doInBackground()");
        // TODO - lock/synchronise field access?

        Color [][] colors = new Color[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                colors[i][j] = board[i][j].flipAlive() ? COLOR_LIFE : COLOR_NO_LIFE;
            }
        }

        return colors;
    }

    @Override
    protected void done() {
        try {
            // TODO - move set background to LifeButton class

            LOG.info("done()");

            Color[][] colors = get();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    board[i][j].setBackground(colors[i][j]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
