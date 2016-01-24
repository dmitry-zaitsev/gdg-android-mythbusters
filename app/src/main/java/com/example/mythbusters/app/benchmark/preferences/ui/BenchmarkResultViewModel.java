package com.example.mythbusters.app.benchmark.preferences.ui;

/**
 * Results of read/write speed measurement
 */
public class BenchmarkResultViewModel {

    /**
     * Milliseconds it takes to perform one read from the storage.
     */
    public final long millisecondsPerRead;

    /**
     * Milliseconds it takes to perform one write to the storage.
     */
    public final long millisecondsPerWrite;

    public BenchmarkResultViewModel(long millisecondsPerRead, long millisecondsPerWrite) {
        this.millisecondsPerRead = millisecondsPerRead;
        this.millisecondsPerWrite = millisecondsPerWrite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkResultViewModel that = (BenchmarkResultViewModel) o;

        return millisecondsPerRead == that.millisecondsPerRead
                && millisecondsPerWrite == that.millisecondsPerWrite;

    }

    @Override
    public int hashCode() {
        int result = (int) (millisecondsPerRead ^ (millisecondsPerRead >>> 32));
        result = 31 * result + (int) (millisecondsPerWrite ^ (millisecondsPerWrite >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BenchmarkResultViewModel{" +
                "millisecondsPerRead=" + millisecondsPerRead +
                ", millisecondsPerWrite=" + millisecondsPerWrite +
                '}';
    }

}
