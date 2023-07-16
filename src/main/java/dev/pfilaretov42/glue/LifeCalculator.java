package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static dev.pfilaretov42.glue.GlueApplication.COLOR_LIFE;
import static dev.pfilaretov42.glue.GlueApplication.COLOR_NO_LIFE;

public class LifeCalculator extends SwingWorker<Color, Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LifeCalculator.class);

    private final LifeButton lifeButton;

    public LifeCalculator(LifeButton lifeButton) {
        this.lifeButton = lifeButton;
    }

    @Override
    protected Color doInBackground() {
        LOG.info("doInBackground...");
        // TODO - lock/synchronise field access?
        return lifeButton.flipAlive() ? COLOR_NO_LIFE : COLOR_LIFE;
    }

    @Override
    protected void done() {
        try {
            LOG.info("done...");
            lifeButton.setBackground(get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
