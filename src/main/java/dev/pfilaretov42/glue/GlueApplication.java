package dev.pfilaretov42.glue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class GlueApplication implements CommandLineRunner {

    // TODO - move constants?
    public static final Color COLOR_LIFE = Color.ORANGE;
    public static final Color COLOR_NO_LIFE = Color.LIGHT_GRAY;
    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GlueApplication.class).headless(false).run(args);
    }

    @Autowired
    private LifeBoard lifeBoard;

    @Autowired
    private StartActionListener startActionListener;

    @Override
    public void run(String... args) {
        JFrame frame = new JFrame("The Game of Life, the Universe, and Everything");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // field
        JPanel fieldPanel = new JPanel(new GridLayout(ROWS, COLUMNS, 3, 3));
        lifeBoard.init(fieldPanel);

        // controls
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        controlPanel.setBounds(0, 0, 800, 100);

        JButton startButton = new JButton();
        controlPanel.add(startButton);
        startButton.setText("Start");
        startButton.addActionListener(startActionListener);

        JButton stopButton = new JButton();
        controlPanel.add(stopButton);
        stopButton.setText("Stop");
        // TODO
//        stopButton.addActionListener();

        JButton resetButton = new JButton();
        controlPanel.add(resetButton);
        resetButton.setText("Reset");
        // TODO
//        resetButton.addActionListener();

        // add panels
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(fieldPanel);
        frame.setVisible(true);
    }
}
