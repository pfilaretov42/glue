package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.config.GlueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LifeBoard {

    private static final Logger LOG = LoggerFactory.getLogger(LifeBoard.class);

    private final Cell[][] board;

    public LifeBoard(GlueProperties properties) {
        int rows = properties.board().rows();
        int columns = properties.board().columns();

        board = new Cell[rows][columns];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Cell(random.nextBoolean());
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }
}
