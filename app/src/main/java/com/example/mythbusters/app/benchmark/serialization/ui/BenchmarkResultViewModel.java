package com.example.mythbusters.app.benchmark.serialization.ui;

/**
 * Result of benchmark
 */
public class BenchmarkResultViewModel {

    /**
     * Nanoseconds spent on a single operation
     */
    public final long nanosecondsPerOperation;

    public BenchmarkResultViewModel(long nanosecondsPerOperation) {
        this.nanosecondsPerOperation = nanosecondsPerOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkResultViewModel that = (BenchmarkResultViewModel) o;

        return nanosecondsPerOperation == that.nanosecondsPerOperation;
    }

    @Override
    public int hashCode() {
        return (int) (nanosecondsPerOperation ^ (nanosecondsPerOperation >>> 32));
    }

    @Override
    public String toString() {
        return "BenchmarkResultViewModel{" +
                "nanosecondsPerOperation=" + nanosecondsPerOperation +
                '}';
    }

}
