package com.example.mythbusters.core.benchmark.serialization;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.util.List;

import rx.Observable;

import static java.util.Collections.unmodifiableList;

/**
 * Measures performance of serialization of {@link android.os.Parcelable} objects.
 */
public class MeasureSerializationUseCase {

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
        throw new UnsupportedOperationException();
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
        public final List<MeasurementResult> smallObjectSerializationMs;

        /**
         * Measurement results of serialization of big objects
         */
        public final List<MeasurementResult> bigObjectSerializationMs;

        public Result(List<MeasurementResult> smallObjectSerializationMs,
                      List<MeasurementResult> bigObjectSerializationMs) {

            this.smallObjectSerializationMs = unmodifiableList(smallObjectSerializationMs);
            this.bigObjectSerializationMs = unmodifiableList(bigObjectSerializationMs);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            return smallObjectSerializationMs.equals(result.smallObjectSerializationMs)
                    && bigObjectSerializationMs.equals(result.bigObjectSerializationMs);
        }

        @Override
        public int hashCode() {
            int result = smallObjectSerializationMs.hashCode();
            result = 31 * result + bigObjectSerializationMs.hashCode();
            return result;
        }

    }

}
