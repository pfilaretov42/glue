package dev.pfilaretov42.glue;

import javax.swing.*;

public class StartButton extends JButton {

    public StartButton(StartActionListener startActionListener) {
        setText("Start");
        addActionListener(startActionListener);
    }
}
