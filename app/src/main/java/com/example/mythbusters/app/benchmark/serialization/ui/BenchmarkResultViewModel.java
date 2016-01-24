package com.example.mythbusters.app.benchmark.serialization.ui;

/**
 * Result of benchmark
 */
public class BenchmarkResultViewModel {

    /**
     * Milliseconds spent on a single operation
     */
    public final long millisecondsPerOperation;

    public BenchmarkResultViewModel(long millisecondsPerOperation) {
        this.millisecondsPerOperation = millisecondsPerOperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkResultViewModel that = (BenchmarkResultViewModel) o;

        return millisecondsPerOperation == that.millisecondsPerOperation;
    }

    @Override
    public int hashCode() {
        return (int) (millisecondsPerOperation ^ (millisecondsPerOperation >>> 32));
    }

    @Override
    public String toString() {
        return "BenchmarkResultViewModel{" +
                "millisecondsPerOperation=" + millisecondsPerOperation +
                '}';
    }

}
