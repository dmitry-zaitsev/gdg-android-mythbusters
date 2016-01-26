package com.example.mythbusters.domain.measurement;

import java.util.List;

import rx.Observable;

import static rx.Observable.from;

/**
 * Auxiliary mathematical operations for measumenets
 */
public class MeasurementMath {

    /**
     * @return average time in nanoseconds it takes to complete one operation
     */
    public static long averageTimePerIteration(MeasurementResult result) {
        return averageInNanoseconds(result.timeMs, result.iterations);
    }

    /**
     * @return average time in nanoseconds it takes to complete one operation
     */
    public static long averageTimePerIteration(List<MeasurementResult> results) {
        final Observable<Long> iterations = from(results)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.iterations
                );

        return from(results)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.timeMs
                )
                .withLatestFrom(
                        iterations,
                        MeasurementMath::averageInNanoseconds
                )
                .toBlocking()
                .first();
    }

    private static long averageInNanoseconds(long totalTime, long totalIterations) {
        return (long) (totalTime / (double) totalIterations);
    }

}
