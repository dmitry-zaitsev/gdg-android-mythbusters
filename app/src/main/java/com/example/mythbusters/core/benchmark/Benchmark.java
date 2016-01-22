package com.example.mythbusters.core.benchmark;

/**
 * Measures time it takes to perform operations
 */
public interface Benchmark {

    /**
     * @param operation          operation to measure
     * @param numberOfIterations how much times to execute operation
     * @return time in milliseconds it took
     */
    long measureOperation(Runnable operation, long numberOfIterations);

}
