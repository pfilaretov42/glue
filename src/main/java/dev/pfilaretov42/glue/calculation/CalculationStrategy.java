package dev.pfilaretov42.glue.calculation;

import java.util.concurrent.CompletableFuture;

public interface CalculationStrategy {

    CompletableFuture<Void> calculateCell(Runnable runnable);
}
