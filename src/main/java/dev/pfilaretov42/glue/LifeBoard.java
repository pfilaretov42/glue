package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.config.GlueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LifeBoard {

    private static final Logger LOG = LoggerFactory.getLogger(LifeBoard.class);

    private final Cell[][] board;

    public LifeBoard(GlueProperties properties) {
        int rows = properties.board().rows();
        int columns = properties.board().columns();

        board = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // start with glider
                int rowOffset = 0;
                int columnOffset = 0;
                boolean isAlive =
                        (i == rows - 1 - rowOffset && j == columns - 2 - columnOffset) ||
                                (i == rows - 2 - rowOffset && j == columns - 3 - columnOffset) ||
                                (i == rows - 3 - rowOffset && j == columns - 1 - columnOffset) ||
                                (i == rows - 3 - rowOffset && j == columns - 2 - columnOffset) ||
                                (i == rows - 3 - rowOffset && j == columns - 3 - columnOffset);
                board[i][j] = new Cell(isAlive);
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }
}
