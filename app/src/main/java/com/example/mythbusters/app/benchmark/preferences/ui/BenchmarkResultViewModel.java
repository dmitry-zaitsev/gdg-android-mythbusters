package com.example.mythbusters.app.benchmark.preferences.ui;

/**
 * Results of read/write speed measurement
 */
public class BenchmarkResultViewModel {

    /**
     * Nanoseconds it takes to perform one read from the storage.
     */
    public final long nanosecondsPerRead;

    /**
     * Nanoseconds it takes to perform one write to the storage.
     */
    public final long nanosecondsPerWrite;

    public BenchmarkResultViewModel(long nanosecondsPerRead, long nanosecondsPerWrite) {
        this.nanosecondsPerRead = nanosecondsPerRead;
        this.nanosecondsPerWrite = nanosecondsPerWrite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkResultViewModel that = (BenchmarkResultViewModel) o;

        return nanosecondsPerRead == that.nanosecondsPerRead
                && nanosecondsPerWrite == that.nanosecondsPerWrite;

    }

    @Override
    public int hashCode() {
        int result = (int) (nanosecondsPerRead ^ (nanosecondsPerRead >>> 32));
        result = 31 * result + (int) (nanosecondsPerWrite ^ (nanosecondsPerWrite >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BenchmarkResultViewModel{" +
                "nanosecondsPerRead=" + nanosecondsPerRead +
                ", nanosecondsPerWrite=" + nanosecondsPerWrite +
                '}';
    }

}
