package dev.pfilaretov42.glue.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Profile("physical-threads")
public class ThreadPoolLogger {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolLogger.class);

    private final ThreadPoolExecutor executor;

    public ThreadPoolLogger(ExecutorService executor) {
        this.executor = (ThreadPoolExecutor) executor;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void logThreadPool() {
        LOG.info("current pool size: {}, largest pool size: {}", executor.getPoolSize(), executor.getLargestPoolSize());
    }
}
