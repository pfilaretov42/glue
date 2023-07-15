package dev.pfilaretov42.glue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class GlueApplication implements CommandLineRunner {

    public static final Color COLOR_LIFE = Color.ORANGE;
    public static final Color COLOR_NO_LIFE = Color.LIGHT_GRAY;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GlueApplication.class).headless(false).run(args);
    }

    @Autowired
    private LifeActionListener lifeActionListener;

    @Override
    public void run(String... args) {
        JFrame frame = new JFrame("The Game of Life, the Universe, and Everything");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // field
        JPanel fieldPanel = new JPanel(new GridLayout(10, 10, 3, 3));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                fieldPanel.add(button);

                button.setFocusable(false);
                // this is how background is set:
                button.setOpaque(true);
                button.setBorderPainted(false);

                // initial state of the field
                if (i == j && i > 2 && i < 7) {
                    button.setBackground(COLOR_LIFE);
                } else {
                    button.setBackground(COLOR_NO_LIFE);
                }

                button.addActionListener(lifeActionListener);
            }
        }

        // controls
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        controlPanel.setBounds(0, 0, 800, 100);

        JButton startButton = new JButton();
        controlPanel.add(startButton);
        startButton.setText("Start");

        JButton stopButton = new JButton();
        controlPanel.add(stopButton);
        stopButton.setText("Stop");

        JButton resetButton = new JButton();
        controlPanel.add(resetButton);
        resetButton.setText("Reset");

        // add panels
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(fieldPanel);
        frame.setVisible(true);
    }
}
