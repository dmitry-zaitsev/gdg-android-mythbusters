package com.example.mythbusters.core.benchmark;

import com.example.mythbusters.domain.measurement.MeasurementResult;

/**
 * Measures time it takes to perform operations
 */
public interface Benchmark {

    /**
     * @param operation          operation to measure
     * @param numberOfIterations how much times to execute operation
     * @return time in milliseconds it took
     */
    MeasurementResult measureOperation(Runnable operation, long numberOfIterations);

}
