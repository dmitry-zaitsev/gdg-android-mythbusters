package com.example.mythbusters.domain.measurement;

import com.example.mythbusters.core.benchmark.Benchmark;

/**
 * Benchmark which measures time of execution by simply measuring time before and after execution.
 */
public class TimerBenchmark implements Benchmark {

    private final Clock clock;

    /**
     * @return {@link TimerBenchmark} which measures time using most-precise system timer.
     * @see System#nanoTime()
     */
    public static TimerBenchmark nanoTimer() {
        return new TimerBenchmark(new SystemNanoClock());
    }

    public TimerBenchmark(Clock clock) {
        this.clock = clock;
    }

    @Override
    public MeasurementResult measureOperation(Runnable operation, long numberOfIterations) {
        ensurePreconditions(numberOfIterations);

        final long startTime = clock.getCurrentTime();

        runBenchmark(operation, numberOfIterations);

        final long endTime = clock.getCurrentTime();

        return new MeasurementResult(
                numberOfIterations,
                endTime - startTime
        );
    }

    private void runBenchmark(Runnable operation, long numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++) {
            operation.run();
        }
    }

    private void ensurePreconditions(long numberOfIterations) {
        if (numberOfIterations < 1) {
            throw new IllegalArgumentException(
                    "Number of iterations must be greater than 0. Was " + numberOfIterations
            );
        }
    }

    /**
     * Provides current time. Format is not specified, but it is guaranteed that values are
     * comparable to one another.
     */
    public interface Clock {

        /**
         * @return current time
         */
        long getCurrentTime();

    }

    private static class SystemNanoClock implements Clock {

        @Override
        public long getCurrentTime() {
            return System.nanoTime();
        }

    }

}
