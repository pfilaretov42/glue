package dev.pfilaretov42.glue;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class StartButton extends JButton {

    public StartButton(StartActionListener startActionListener) {
        setText("Start");
        addActionListener(startActionListener);
    }
}
