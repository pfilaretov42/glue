package dev.pfilaretov42.glue;

import javax.swing.*;

public class LifeButton extends JButton {
    private boolean isCurrentlyAlive;
    private boolean isFutureAlive;

    public LifeButton(boolean isCurrentlyAlive) {
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
