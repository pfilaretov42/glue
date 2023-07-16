package dev.pfilaretov42.glue.config;

import dev.pfilaretov42.glue.LifeBoard;
import dev.pfilaretov42.glue.LifeCalculator;
import dev.pfilaretov42.glue.StartActionListener;
import dev.pfilaretov42.glue.StartButton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class GlueConfig {
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public LifeCalculator lifeCalculator(LifeBoard lifeBoard, StartActionListener startActionListener) {
        return new LifeCalculator(lifeBoard) {
            @Override
            protected StartButton getStartButton() {
                return startButton(startActionListener);
            }
        };
    }

    @Bean
    public StartActionListener startActionListener(LifeBoard lifeBoard) {
        return new StartActionListener() {
            @Override
            protected LifeCalculator getLifeCalculator() {
                return lifeCalculator(lifeBoard, this);
            }
        };
    }

    @Bean
    public StartButton startButton(StartActionListener startActionListener) {
        return new StartButton(startActionListener);
    }
}
