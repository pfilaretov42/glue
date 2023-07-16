package dev.pfilaretov42.glue;

import javax.swing.*;

public class LifeButton extends JButton {
    private boolean isAlive;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean flipAlive() {
        boolean tmp = isAlive;
        isAlive = !isAlive;
        return tmp;
    }
}
