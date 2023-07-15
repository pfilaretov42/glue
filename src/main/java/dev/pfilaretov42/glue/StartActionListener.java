package dev.pfilaretov42.glue;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class StartActionListener implements ActionListener {
    private final LifeField lifeField;

    public StartActionListener(LifeField lifeField) {
        this.lifeField = lifeField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            lifeField.run();
        }
    }
}
