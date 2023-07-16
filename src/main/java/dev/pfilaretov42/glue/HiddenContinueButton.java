package dev.pfilaretov42.glue;

import javax.swing.*;

public class HiddenContinueButton extends JButton {

    public HiddenContinueButton(StartActionListener startActionListener) {
        setVisible(false);
        setSize(0, 0);
        addActionListener(startActionListener);
    }
}
