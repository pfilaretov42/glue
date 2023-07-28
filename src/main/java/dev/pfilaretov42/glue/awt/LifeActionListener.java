package dev.pfilaretov42.glue.awt;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class LifeActionListener implements ActionListener {

    private static final Color COLOR_LIFE = Color.ORANGE;
    private static final Color COLOR_NO_LIFE = Color.LIGHT_GRAY;

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
