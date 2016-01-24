package com.example.mythbusters.core.benchmark.io;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Measures performace of write and read operations into storage.
 */
public class MeasureWriteReadUseCase {

    private static final int[] ITERATIONS = {
            1,
            10,
            100,
            1000
    };

    private static final String KEY_NAME = "key";

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
        return Observable.create(subscriber -> {
            Result result = performMeasurement();

            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(result);
            }
        });
    }

    private Result performMeasurement() {
        List<MeasurementResult> writeResult = measureWriting();
        List<MeasurementResult> readResult = measureReading();

        return new Result(writeResult, readResult);
    }

    private List<MeasurementResult> measureWriting() {
        final ArrayList<MeasurementResult> results = new ArrayList<>();

        final String value = valueProvider.provide();

        for (int iterations : ITERATIONS) {
            results.add(
                    runBenchmarkForWriting(value, iterations)
            );
        }
        return results;
    }

    private MeasurementResult runBenchmarkForWriting(String value, int iterations) {
        return benchmark.measureOperation(
                new WriteOperation(value),
                iterations
        );
    }

    private List<MeasurementResult> measureReading() {
        final ArrayList<MeasurementResult> results = new ArrayList<>();

        for (int iterations : ITERATIONS) {
            results.add(
                    runBenchmarkForReading(iterations)
            );
        }

        return results;
    }

    private MeasurementResult runBenchmarkForReading(int iterations) {
        return benchmark.measureOperation(
                new ReadOperation(),
                iterations
        );
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

        public Result(List<MeasurementResult> writes, List<MeasurementResult> reads) {
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

    private class WriteOperation implements Runnable {

        private final String value;

        private int index = 0;

        private WriteOperation(String value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                storage.write(KEY_NAME + index, value);
                index++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private class ReadOperation implements Runnable {

        private int index = 0;

        @Override
        public void run() {
            try {
                storage.read(KEY_NAME + index);
                index++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
