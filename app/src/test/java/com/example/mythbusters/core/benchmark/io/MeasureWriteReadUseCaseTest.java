package com.example.mythbusters.core.benchmark.io;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.core.benchmark.MockBenchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import rx.observers.TestSubscriber;

import static com.example.mythbusters.core.benchmark.BenchmarkUtils.buildMeasurementResult;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

public class MeasureWriteReadUseCaseTest {

    @Spy
    Benchmark benchmark = new MockBenchmark();
    @Mock
    Storage storage;
    @Mock
    MeasureWriteReadUseCase.ValueProvider valueProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doReturn("value")
                .when(valueProvider)
                .provide();

        doReturn("value")
                .when(storage)
                .read(anyString());
    }

    @Test
    public void behavior() throws Exception {
        // Given
        MeasureWriteReadUseCase useCase = new MeasureWriteReadUseCase(
                benchmark,
                storage,
                valueProvider
        );

        // When
        useCase.measure()
                .subscribe();

        // Then
        verifyWritingBenchmarked();
        verifyReadingBenchmarked();
    }

    private void verifyWritingBenchmarked() throws Exception {
        InOrder inOrder = inOrder(benchmark, storage, valueProvider);

        inOrder.verify(valueProvider).provide();
        verifyValueWritten(inOrder, 1);
        verifyValueWritten(inOrder, 10);
        verifyValueWritten(inOrder, 100);
        verifyValueWritten(inOrder, 1000);
    }

    private void verifyValueWritten(InOrder inOrder, long iterations) throws Exception {
        inOrder.verify(benchmark).measureOperation(
                any(Runnable.class),
                eq(iterations)
        );

        inOrder.verify(storage, times((int) iterations))
                .write(
                        anyString(),
                        eq("value")
                );
    }

    private void verifyReadingBenchmarked() throws Exception {
        InOrder inOrder = inOrder(benchmark, storage, valueProvider);

        verifyValueRead(inOrder, 1);
        verifyValueRead(inOrder, 10);
        verifyValueRead(inOrder, 100);
        verifyValueRead(inOrder, 1000);
    }

    private void verifyValueRead(InOrder inOrder, long iterations) throws Exception {
        inOrder.verify(benchmark).measureOperation(
                any(Runnable.class),
                eq(iterations)
        );

        inOrder.verify(storage, times((int) iterations))
                .read(anyString());
    }

    @Test
    public void output() throws Exception {
        // Given
        MeasureWriteReadUseCase useCase = new MeasureWriteReadUseCase(
                benchmark,
                storage,
                valueProvider
        );

        TestSubscriber<MeasureWriteReadUseCase.Result> subscriber = new TestSubscriber<>();

        MeasureWriteReadUseCase.Result expected = buildExpectedResult();

        // When
        useCase.measure()
                .subscribe(subscriber);

        // Then
        subscriber.assertValue(expected);
    }

    private MeasureWriteReadUseCase.Result buildExpectedResult() {
        final List<MeasurementResult> results = asList(
                buildMeasurementResult(1),
                buildMeasurementResult(10),
                buildMeasurementResult(100),
                buildMeasurementResult(1000)
        );

        return new MeasureWriteReadUseCase.Result(
                results,
                results
        );
    }
}