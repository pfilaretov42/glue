package dev.pfilaretov42.glue.config;

import dev.pfilaretov42.glue.*;
import dev.pfilaretov42.glue.calculation.CalculationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class GlueConfig {

    // TODO - configure via properties, whether to create executor or not and which type of executor
    @Bean
    public ExecutorService executor() {
        return Executors.newVirtualThreadPerTaskExecutor();
//        return Executors.newCachedThreadPool();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public LifeCalculator lifeCalculator(
            LifeBoard lifeBoard,
            BoardTextArea boardTextArea,
            StartActionListener startActionListener,
            GlueProperties properties,
            ExecutorService executor,
            CalculationStrategy calculationStrategy
    ) {
        return new LifeCalculator(lifeBoard, boardTextArea, properties, executor, calculationStrategy) {
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
            ExecutorService executor,
            CalculationStrategy calculationStrategy
    ) {
        return new StartActionListener() {
            @Override
            protected LifeCalculator getLifeCalculator() {
                return lifeCalculator(
                        lifeBoard, boardTextArea, this, properties, executor, calculationStrategy);
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
