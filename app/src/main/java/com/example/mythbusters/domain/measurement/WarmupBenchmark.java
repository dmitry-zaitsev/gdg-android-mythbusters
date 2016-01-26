package com.example.mythbusters.domain.measurement;

import com.example.mythbusters.core.benchmark.Benchmark;

/**
 * "Warms up" before starting measuring the operation by calling to it multiple times.
 */
public class WarmupBenchmark implements Benchmark {

    private static final int DEFAULT_WARM_UP_CYCLES = 10000;

    private final Benchmark effectiveBenchmark;
    private final int warmUpCycles;

    /**
     * @return new instance of {@link WarmupBenchmark} with default amount of warm up cycles.
     */
    public static WarmupBenchmark warmUp(Benchmark benchmark) {
        return new WarmupBenchmark(benchmark, DEFAULT_WARM_UP_CYCLES);
    }

    /**
     * @return new instance of {@link WarmupBenchmark} with given amount of warm up cycles
     */
    public static WarmupBenchmark warmUp(Benchmark benchmark, int warmUpCycles) {
        return new WarmupBenchmark(benchmark, warmUpCycles);
    }

    public WarmupBenchmark(Benchmark effectiveBenchmark, int warmUpCycles) {
        this.effectiveBenchmark = effectiveBenchmark;
        this.warmUpCycles = warmUpCycles;
    }

    @Override
    public MeasurementResult measureOperation(Runnable operation, long numberOfIterations) {
        warmUp(operation);

        return effectiveBenchmark.measureOperation(operation, numberOfIterations);
    }

    private void warmUp(Runnable operation) {
        for (int i = 0; i < warmUpCycles; i++) {
            operation.run();
        }
    }

}
