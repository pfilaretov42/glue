package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.config.GlueProperties;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class BoardTextArea extends JTextArea {

    private static final char ALIVE = '◉'; // ▣ ◼ ◌
    private static final char DEAD = ' ';
    private static final char DELIMITER = '|';

    public BoardTextArea(LifeBoard lifeBoard, GlueProperties properties) {
        setEditable(false);
        setFont(new Font("Courier New", Font.PLAIN, properties.board().fontSize()));
        updateView(lifeBoard.getBoard());
    }

    public void updateView(Cell[][] board) {
        StringBuilder sb = new StringBuilder();
        for (Cell[] cellRows : board) {
            sb.append(DELIMITER);
            for (Cell cell : cellRows) {
                sb.append(cell.isCurrentlyAlive() ? ALIVE : DEAD);
                sb.append(DELIMITER);
            }
            sb.append('\n');
        }

        setText(sb.toString());
    }
}
