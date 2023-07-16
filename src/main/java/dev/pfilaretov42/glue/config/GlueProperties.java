package dev.pfilaretov42.glue.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "glue")
public record GlueProperties(Window window, Board board, Calculation calculation) {

    public record Window(int height, int width) {
    }

    public record Board(int rows, int columns, int fontSize) {
    }

    public record Calculation(int delay) {
    }
}
