package com.example.mythbusters.core.benchmark.serialization;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static java.util.Collections.unmodifiableList;

/**
 * Measures performance of serialization of {@link android.os.Parcelable} objects.
 */
public class MeasureSerializationUseCase {

    private static final int[] ITERATIONS = {
            10,
            100,
            1000,
            10000
    };

    private final Benchmark benchmark;
    private final Serializer serializer;
    private final ObjectFactory objectFactory;

    public MeasureSerializationUseCase(Benchmark benchmark,
                                       Serializer serializer,
                                       ObjectFactory objectFactory) {
        this.benchmark = benchmark;
        this.serializer = serializer;
        this.objectFactory = objectFactory;
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
        List<MeasurementResult> smallObjectsResult = measureSmallObjects();
        List<MeasurementResult> bigObjectsResult = measureBigObjects();

        return new Result(smallObjectsResult, bigObjectsResult);
    }

    private List<MeasurementResult> measureSmallObjects() {
        final Object smallObject = objectFactory.createSmallObject();

        return measureObjectSerialization(smallObject);
    }

    private List<MeasurementResult> measureBigObjects() {
        final Object bigObject = objectFactory.createBigObject();

        return measureObjectSerialization(bigObject);
    }

    private List<MeasurementResult> measureObjectSerialization(Object object) {
        final ArrayList<MeasurementResult> results = new ArrayList<>(ITERATIONS.length);

        for (int iterations : ITERATIONS) {
            results.add(
                    runBenchmark(object, iterations)
            );
        }

        return results;
    }

    private MeasurementResult runBenchmark(Object object, int iterations) {
        final long time = benchmark.measureOperation(
                () -> serializer.serialize(object),
                iterations
        );

        return new MeasurementResult(iterations, time);
    }

    /**
     * Creates objects for serialization
     */
    public interface ObjectFactory {

        /**
         * @return "small" object for serialization
         */
        Object createSmallObject();

        /**
         * @return "big" object for serialization
         */
        Object createBigObject();

    }

    /**
     * Result of the measurement
     */
    public static class Result {

        /**
         * Measurement results of serialization of small objects
         */
        public final List<MeasurementResult> smallObjectSerialization;

        /**
         * Measurement results of serialization of big objects
         */
        public final List<MeasurementResult> bigObjectSerialization;

        public Result(List<MeasurementResult> smallObjectSerialization,
                      List<MeasurementResult> bigObjectSerialization) {

            this.smallObjectSerialization = unmodifiableList(smallObjectSerialization);
            this.bigObjectSerialization = unmodifiableList(bigObjectSerialization);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            return smallObjectSerialization.equals(result.smallObjectSerialization)
                    && bigObjectSerialization.equals(result.bigObjectSerialization);
        }

        @Override
        public int hashCode() {
            int result = smallObjectSerialization.hashCode();
            result = 31 * result + bigObjectSerialization.hashCode();
            return result;
        }

    }

}
