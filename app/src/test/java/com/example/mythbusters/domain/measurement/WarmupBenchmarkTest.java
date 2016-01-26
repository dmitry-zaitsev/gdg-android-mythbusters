package com.example.mythbusters.domain.measurement;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.core.benchmark.MockBenchmark;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WarmupBenchmarkTest {

    @Spy
    Benchmark effectiveBenchmark = new MockBenchmark();
    @Mock
    Runnable operation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void warmUpAndMeasure() throws Exception {
        // Given
        WarmupBenchmark benchmark = new WarmupBenchmark(effectiveBenchmark, 3);

        // When
        MeasurementResult result = benchmark.measureOperation(operation, 5);

        // Then
        verify(operation, times(3 + 5)).run();

        assertEquals(
                new MeasurementResult(5, MockBenchmark.MEASUREMENT_MS),
                result
        );
    }
}