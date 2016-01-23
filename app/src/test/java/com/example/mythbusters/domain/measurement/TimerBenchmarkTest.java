package com.example.mythbusters.domain.measurement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TimerBenchmarkTest {

    @Mock
    TimerBenchmark.Clock clock;
    @Mock
    Runnable operation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void measureOperation() throws Exception {
        // Given
        when(clock.getCurrentTime()).thenReturn(10L, 100L);

        TimerBenchmark benchmark = new TimerBenchmark(clock);

        final MeasurementResult expected = new MeasurementResult(
                10,
                90L
        );

        // When
        MeasurementResult result = benchmark.measureOperation(
                operation,
                10
        );

        // Then
        verify(operation, times(10)).run();
        verify(clock, times(2)).getCurrentTime();

        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwForInvalidIterationsCount() throws Exception {
        // Given
        TimerBenchmark benchmark = new TimerBenchmark(clock);

        // When
        benchmark.measureOperation(
                operation,
                -10
        );

        // Then
        // IllegalArgumentException is thrown
    }

}