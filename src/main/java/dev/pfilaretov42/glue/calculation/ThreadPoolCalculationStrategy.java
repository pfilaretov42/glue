package dev.pfilaretov42.glue.calculation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@Profile("thread-pool")
public class ThreadPoolCalculationStrategy implements CalculationStrategy {

    private final ExecutorService executor;

    public ThreadPoolCalculationStrategy(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public CompletableFuture<Void> calculateCell(Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();
            return null;
        }, executor);
    }
}
