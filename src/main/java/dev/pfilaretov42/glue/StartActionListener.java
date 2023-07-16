package dev.pfilaretov42.glue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class StartActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            // TODO - disable Start button
            getLifeCalculator().execute();
        }
    }

    protected abstract LifeCalculator getLifeCalculator();
}
