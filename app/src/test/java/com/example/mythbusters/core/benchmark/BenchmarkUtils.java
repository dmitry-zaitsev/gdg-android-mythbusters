package com.example.mythbusters.core.benchmark;

import com.example.mythbusters.domain.measurement.MeasurementResult;

/**
 * Utils for testing benchmark package
 */
public class BenchmarkUtils {

    /**
     * @return measurement result for given amount of iterations with properly
     * set time
     */
    public static MeasurementResult buildMeasurementResult(int iterations) {
        return new MeasurementResult(
                iterations,
                MockBenchmark.MEASUREMENT_MS
        );
    }

}
