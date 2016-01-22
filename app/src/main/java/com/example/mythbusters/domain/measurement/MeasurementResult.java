package com.example.mythbusters.domain.measurement;

/**
 * Result of the measurement
 */
public class MeasurementResult {

    /**
     * Number of iterations which were measured
     */
    public final long iterations;

    /**
     * Time (in milliseconds) it took to perform iterations.
     */
    public final long timeMs;

    public MeasurementResult(long iterations, long timeMs) {
        this.iterations = iterations;
        this.timeMs = timeMs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementResult that = (MeasurementResult) o;

        return iterations == that.iterations && timeMs == that.timeMs;
    }

    @Override
    public int hashCode() {
        int result = (int) (iterations ^ (iterations >>> 32));
        result = 31 * result + (int) (timeMs ^ (timeMs >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementResult{" +
                "iterations=" + iterations +
                ", timeMs=" + timeMs +
                '}';
    }

}
