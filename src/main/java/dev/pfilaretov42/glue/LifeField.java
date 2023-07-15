package dev.pfilaretov42.glue;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static dev.pfilaretov42.glue.GlueApplication.*;

@Component
public class LifeField {

    private final LifeActionListener lifeActionListener;
    private final JButton[][] field = new JButton[ROWS][COLUMNS];

    public LifeField(LifeActionListener lifeActionListener) {
        this.lifeActionListener = lifeActionListener;
    }

    public void init(JPanel panel) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                field[i][j] = new JButton();
                panel.add(field[i][j]);

                field[i][j].setFocusable(false);
                // this is how background is set:
                field[i][j].setOpaque(true);
                field[i][j].setBorderPainted(false);

                // initial state of the field
                if (i == j && i > 2 && i < 7) {
                    field[i][j].setBackground(COLOR_LIFE);
                } else {
                    field[i][j].setBackground(COLOR_NO_LIFE);
                }

                field[i][j].addActionListener(lifeActionListener);
            }
        }
    }

    public void run() {
        // TODO - run the game
        field[0][0].setBackground(Color.BLACK);
    }
}
