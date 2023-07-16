package dev.pfilaretov42.glue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static dev.pfilaretov42.glue.GlueApplication.COLUMNS;
import static dev.pfilaretov42.glue.GlueApplication.ROWS;

@Component
public class LifeBoard {

    private static final Logger LOG = LoggerFactory.getLogger(LifeBoard.class);

    private final Cell[][] board = new Cell[ROWS][COLUMNS];

    public LifeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                // start with glider
                int rowOffset = 5;
                int columnOffset = 5;
                boolean isAlive =
                        (i == ROWS - 1 - rowOffset && j == COLUMNS - 2 - columnOffset) ||
                                (i == ROWS - 2 - rowOffset && j == COLUMNS - 3 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 1 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 2 - columnOffset) ||
                                (i == ROWS - 3 - rowOffset && j == COLUMNS - 3 - columnOffset);
                board[i][j] = new Cell(isAlive);
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }
}
