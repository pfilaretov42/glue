package dev.pfilaretov42.glue.model;

public class Cell {
    private boolean isCurrentlyAlive;
    private boolean isFutureAlive;

    public Cell(boolean isCurrentlyAlive) {
        this.isCurrentlyAlive = isCurrentlyAlive;
        isFutureAlive = isCurrentlyAlive;
    }

    public boolean isCurrentlyAlive() {
        return isCurrentlyAlive;
    }

    public boolean isFutureAlive() {
        return isFutureAlive;
    }

    public void setFutureAlive(boolean futureAlive) {
        isFutureAlive = futureAlive;
    }

    public void updateCurrentAliveStatus() {
        isCurrentlyAlive = isFutureAlive;
    }
}
