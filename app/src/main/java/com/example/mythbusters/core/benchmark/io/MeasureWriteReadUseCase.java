package com.example.mythbusters.core.benchmark.io;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.util.List;

import rx.Observable;

/**
 * Measures performace of write and read operations into storage.
 */
public class MeasureWriteReadUseCase {

    private final Benchmark benchmark;
    private final Storage storage;
    private final ValueProvider valueProvider;

    public MeasureWriteReadUseCase(Benchmark benchmark,
                                   Storage storage,
                                   ValueProvider valueProvider) {
        this.benchmark = benchmark;
        this.storage = storage;
        this.valueProvider = valueProvider;
    }

    /**
     * @return {@link Observable} which produces single result of measurement.
     */
    public Observable<Result> measure() {
        throw new UnsupportedOperationException();
    }

    /**
     * Provides value to be written into {@link Storage}
     */
    public interface ValueProvider {

        /**
         * @return value to be inserted into the storage
         */
        String provide();

    }

    /**
     * Result of the measurement
     */
    public static class Result {

        /**
         * Measurement results of write operations
         */
        public final List<MeasurementResult> writes;

        /**
         * Measurement results of read operations
         */
        public final List<MeasurementResult> reads;

        Result(List<MeasurementResult> writes, List<MeasurementResult> reads) {
            this.writes = writes;
            this.reads = reads;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            return writes.equals(result.writes) && reads.equals(result.reads);
        }

        @Override
        public int hashCode() {
            int result = writes.hashCode();
            result = 31 * result + reads.hashCode();
            return result;
        }
    }

}
