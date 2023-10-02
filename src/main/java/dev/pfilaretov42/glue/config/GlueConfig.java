package dev.pfilaretov42.glue.config;

import dev.pfilaretov42.glue.awt.BoardTextArea;
import dev.pfilaretov42.glue.awt.HiddenContinueButton;
import dev.pfilaretov42.glue.awt.StartActionListener;
import dev.pfilaretov42.glue.awt.StartButton;
import dev.pfilaretov42.glue.calculation.CalculationStrategy;
import dev.pfilaretov42.glue.calculation.LifeCalculator;
import dev.pfilaretov42.glue.model.LifeBoard;
import dev.pfilaretov42.glue.util.BoardUpdateTimeLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.swing.*;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class GlueConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public LifeCalculator lifeCalculator(
        LifeBoard lifeBoard,
        BoardTextArea boardTextArea,
        StartActionListener startActionListener,
        GlueProperties properties,
        CalculationStrategy calculationStrategy,
        BoardUpdateTimeLogger boardUpdateTimeLogger
    ) {
        return new LifeCalculator(lifeBoard, boardTextArea, properties, calculationStrategy, boardUpdateTimeLogger) {
            @Override
            protected JButton getContinueButton() {
                return hiddenContinueButton(startActionListener);
            }
        };
    }

    @Bean
    public StartActionListener startActionListener(
        LifeBoard lifeBoard,
        BoardTextArea boardTextArea,
        GlueProperties properties,
        CalculationStrategy calculationStrategy,
        BoardUpdateTimeLogger boardUpdateTimeLogger
    ) {
        return new StartActionListener() {
            @Override
            protected LifeCalculator getLifeCalculator() {
                return lifeCalculator(
                    lifeBoard, boardTextArea, this, properties, calculationStrategy, boardUpdateTimeLogger);
            }
        };
    }

    @Bean
    public StartButton startButton(StartActionListener startActionListener) {
        return new StartButton(startActionListener);
    }

    @Bean
    public HiddenContinueButton hiddenContinueButton(StartActionListener startActionListener) {
        return new HiddenContinueButton(startActionListener);
    }
}
