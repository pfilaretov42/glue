package dev.pfilaretov42.glue.config;

import dev.pfilaretov42.glue.*;
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
            LifeBoard lifeBoard, BoardTextArea boardTextArea, StartActionListener startActionListener) {
        return new LifeCalculator(lifeBoard, boardTextArea) {
            @Override
            protected JButton getContinueButton() {
                return hiddenContinueButton(startActionListener);
            }
        };
    }

    @Bean
    public StartActionListener startActionListener(LifeBoard lifeBoard, BoardTextArea boardTextArea) {
        return new StartActionListener() {
            @Override
            protected LifeCalculator getLifeCalculator() {
                return lifeCalculator(lifeBoard, boardTextArea, this);
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
