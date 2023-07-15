package dev.pfilaretov42.glue;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static dev.pfilaretov42.glue.GlueApplication.COLOR_LIFE;
import static dev.pfilaretov42.glue.GlueApplication.COLOR_NO_LIFE;

@Component
public class LifeActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton button) {
            if (button.getBackground() != COLOR_LIFE) {
                button.setBackground(COLOR_LIFE);
            } else {
                button.setBackground(COLOR_NO_LIFE);
            }
        }
    }
}
