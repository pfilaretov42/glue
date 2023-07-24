package dev.pfilaretov42.glue.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@Profile("thread-pool")
public class ThreadPoolCalculationStrategy implements CalculationStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolCalculationStrategy.class);

    private final ExecutorService executor;

    public ThreadPoolCalculationStrategy(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public CompletableFuture<Void> calculateCell(Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();

//            LOG.info("largest pool size: {}", ((ThreadPoolExecutor) executor).getLargestPoolSize());
//            LOG.info("largest pool size: {}", ((ThreadContainer) executor).getLargestPoolSize());

            return null;
        }, executor);
    }
}
