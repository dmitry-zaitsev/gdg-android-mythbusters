package com.example.mythbusters.core.benchmark;

import com.example.mythbusters.domain.measurement.MeasurementResult;

/**
 * Benchmark for Unit tests which just executes operation specified amount of times.
 * Always outputs constant value of {@link #MEASUREMENT_MS}
 */
public class MockBenchmark implements Benchmark {

    public static final long MEASUREMENT_MS = 100L;

    @Override
    public MeasurementResult measureOperation(Runnable operation, long numberOfIterations) {
        for (long i = 0; i < numberOfIterations; i++) {
            operation.run();
        }

        return new MeasurementResult(numberOfIterations, MEASUREMENT_MS);
    }

}
