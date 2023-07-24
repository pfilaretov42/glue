package dev.pfilaretov42.glue;

import dev.pfilaretov42.glue.awt.BoardTextArea;
import dev.pfilaretov42.glue.awt.HiddenContinueButton;
import dev.pfilaretov42.glue.awt.StartButton;
import dev.pfilaretov42.glue.config.GlueProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
@EnableConfigurationProperties(GlueProperties.class)
public class GlueApplication implements CommandLineRunner {

    // TODO - move constants?
    public static final Color COLOR_LIFE = Color.ORANGE;
    public static final Color COLOR_NO_LIFE = Color.LIGHT_GRAY;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GlueApplication.class).headless(false).run(args);
    }

    private final BoardTextArea boardTextArea;
    private final StartButton startButton;
    private final HiddenContinueButton hiddenContinueButton;
    private final GlueProperties glueProperties;

    public GlueApplication(
            BoardTextArea boardTextArea,
            StartButton startButton,
            HiddenContinueButton hiddenContinueButton,
            GlueProperties glueProperties
    ) {
        this.boardTextArea = boardTextArea;
        this.startButton = startButton;
        this.hiddenContinueButton = hiddenContinueButton;
        this.glueProperties = glueProperties;
    }

    @Override
    public void run(String... args) {
        JFrame frame = new JFrame("The Game of Life, the Universe, and Everything");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(glueProperties.window().width(), glueProperties.window().height());
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // board
        JPanel textBoardPanel = new JPanel(new BorderLayout());
        textBoardPanel.add(boardTextArea);

        // controls
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        controlPanel.setBounds(0, 0, glueProperties.window().width(), 100);

        controlPanel.add(hiddenContinueButton);
        controlPanel.add(startButton);

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
        frame.add(textBoardPanel);
        frame.setVisible(true);
    }
}
