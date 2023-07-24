package dev.pfilaretov42.glue.calculation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Profile("single-thread")
public class SingleThreadCalculationStrategy implements CalculationStrategy {

    @Override
    public CompletableFuture<Void> calculateCell(Runnable runnable) {
        runnable.run();
        return CompletableFuture.completedFuture(null);
    }
}
