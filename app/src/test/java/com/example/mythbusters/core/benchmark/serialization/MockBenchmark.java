package com.example.mythbusters.core.benchmark.serialization;

import com.example.mythbusters.core.benchmark.Benchmark;

/**
 * Benchmark for Unit tests which just executes operation specified amount of times.
 * Always outputs constant value of {@link #MEASUREMENT_MS}
 */
public class MockBenchmark implements Benchmark {

    public static final long MEASUREMENT_MS = 100L;

    @Override
    public long measureOperation(Runnable operation, long numberOfIterations) {
        for (long i = 0; i < numberOfIterations; i++) {
            operation.run();
        }

        return MEASUREMENT_MS;
    }

}
