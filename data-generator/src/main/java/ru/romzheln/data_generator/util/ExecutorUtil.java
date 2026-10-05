package ru.romzheln.data_generator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class ExecutorUtil {

    private ExecutorUtil() {
    }

    public static void execute(int count, int poolSize, ExecutorService executor, Runnable task) {int batchSize = (int) Math.ceil((double) count / poolSize);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < count; i += batchSize) {
            int from = i;
            int to = Math.min(i + batchSize, count);
            futures.add(executor.submit(() -> {
                for (int j = from; j < to; j++) {
                    task.run();
                }
            }));
        }
        waitForCompletion(futures);
    }

    private static void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
