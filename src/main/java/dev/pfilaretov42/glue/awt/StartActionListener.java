package dev.pfilaretov42.glue.awt;

import dev.pfilaretov42.glue.calculation.LifeCalculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class StartActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof StartButton startButton) {
            startButton.setEnabled(false);
        }
        getLifeCalculator().execute();
    }

    protected abstract LifeCalculator getLifeCalculator();
}
