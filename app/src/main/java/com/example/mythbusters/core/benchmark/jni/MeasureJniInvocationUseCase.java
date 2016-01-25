package com.example.mythbusters.core.benchmark.jni;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Measures overhead of JNI invocation versus JVM invocations.
 */
public class MeasureJniInvocationUseCase {

    private static final int[] ITERATIONS = {
            10,
            100,
            1000,
            10000
    };

    private final Benchmark benchmark;
    private final Invocation invocation;

    public MeasureJniInvocationUseCase(Benchmark benchmark, Invocation invocation) {
        this.benchmark = benchmark;
        this.invocation = invocation;
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
        List<MeasurementResult> jvmInvocations = measureJvmInvocations();
        List<MeasurementResult> jniInvocations = measureJniInvocations();

        return new Result(
                jniInvocations,
                jvmInvocations
        );
    }

    private List<MeasurementResult> measureJvmInvocations() {
        final ArrayList<MeasurementResult> results = new ArrayList<>();

        for (int iterations : ITERATIONS) {
            results.add(
                    runBenchmarkForJvm(iterations)
            );
        }

        return results;
    }

    private MeasurementResult runBenchmarkForJvm(int iterations) {
        return benchmark.measureOperation(
                invocation::runOnJvm,
                iterations
        );
    }

    private List<MeasurementResult> measureJniInvocations() {
        final ArrayList<MeasurementResult> results = new ArrayList<>();

        for (int iterations : ITERATIONS) {
            results.add(
                    runBenchmarkForJni(iterations)
            );
        }

        return results;
    }

    private MeasurementResult runBenchmarkForJni(int iterations) {
        return benchmark.measureOperation(
                invocation::runOnJni,
                iterations
        );
    }

    /**
     * Invocation which can be invoked either on JVM or through JNI, natively.
     */
    public interface Invocation {

        /**
         * Invoke on JVM.
         */
        void runOnJvm();

        /**
         * Invoke natively, though JNI.
         */
        void runOnJni();

    }

    /**
     * Result of the measurement
     */
    public static class Result {

        /**
         * Measurement results of JNI invocations
         */
        public final List<MeasurementResult> jniInvocations;

        /**
         * Measurement results of JVM invocations
         */
        public final List<MeasurementResult> jvmInvocations;

        public Result(List<MeasurementResult> jniInvocations,
               List<MeasurementResult> jvmInvocations) {
            this.jniInvocations = jniInvocations;
            this.jvmInvocations = jvmInvocations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            return jniInvocations.equals(result.jniInvocations) && jvmInvocations.equals(result.jvmInvocations);
        }

        @Override
        public int hashCode() {
            int result = jniInvocations.hashCode();
            result = 31 * result + jvmInvocations.hashCode();
            return result;
        }

    }

}
