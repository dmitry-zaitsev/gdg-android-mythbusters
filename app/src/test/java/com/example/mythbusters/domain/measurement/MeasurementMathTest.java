package com.example.mythbusters.domain.measurement;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MeasurementMathTest {

    @Test
    public void averageTimePerIteration() throws Exception {
        // Given
        MeasurementResult input = new MeasurementResult(3, 10);

        long expected = (long) ((10 / 3.0) * 1e3);

        // When
        long result = MeasurementMath.averageTimePerIteration(input);

        // Then
        assertEquals(expected, result);
    }

    @Test
    public void averageTimePerIteration1() throws Exception {
        // Given
        List<MeasurementResult> results = asList(
                new MeasurementResult(3, 10),
                new MeasurementResult(5, 20),
                new MeasurementResult(8, 30)
        );

        long expected = (long) ((10 + 20 + 30) / (3.0 + 5.0 + 8.0) * 1e3);

        // When
        long result = MeasurementMath.averageTimePerIteration(results);

        // Then
        assertEquals(expected, result);
    }

}