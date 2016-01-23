package com.example.mythbusters.core.benchmark.jni;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.core.benchmark.BenchmarkUtils;
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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

public class MeasureJniInvocationUseCaseTest {

    @Spy
    Benchmark benchmark = new MockBenchmark();
    @Mock
    MeasureJniInvocationUseCase.Invocation invocation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void behavior() throws Exception {
        // Given
        MeasureJniInvocationUseCase useCase = new MeasureJniInvocationUseCase(benchmark, invocation);

        // When
        useCase.measure()
                .subscribe();

        // Then
        verifyJvmInvocationBenchmarked();
        verifyJniInvocationBenchmarked();
    }

    private void verifyJvmInvocationBenchmarked() {
        final InOrder inOrder = inOrder(benchmark, invocation);

        verifyJvmInvocationMeasured(inOrder, 10);
        verifyJvmInvocationMeasured(inOrder, 100);
        verifyJvmInvocationMeasured(inOrder, 1000);
        verifyJvmInvocationMeasured(inOrder, 10000);
    }

    private void verifyJvmInvocationMeasured(InOrder inOrder, long iterations) {
        inOrder.verify(benchmark)
                .measureOperation(
                        any(Runnable.class),
                        eq(iterations)
                );
        inOrder.verify(invocation, times((int) iterations))
                .runOnJvm();
    }

    private void verifyJniInvocationBenchmarked() {
        final InOrder inOrder = inOrder(benchmark, invocation);

        verifyJniInvocationMeasured(inOrder, 10);
        verifyJniInvocationMeasured(inOrder, 100);
        verifyJniInvocationMeasured(inOrder, 1000);
        verifyJniInvocationMeasured(inOrder, 10000);
    }

    private void verifyJniInvocationMeasured(InOrder inOrder, long iterations) {
        inOrder.verify(benchmark)
                .measureOperation(
                        any(Runnable.class),
                        eq(iterations)
                );
        inOrder.verify(invocation, times((int) iterations))
                .runOnJni();
    }

    @Test
    public void output() throws Exception {
        // Given
        MeasureJniInvocationUseCase useCase = new MeasureJniInvocationUseCase(benchmark, invocation);

        TestSubscriber<MeasureJniInvocationUseCase.Result> subscriber = new TestSubscriber<>();

        MeasureJniInvocationUseCase.Result expected = buildExpectedResult();

        // When
        useCase.measure()
                .subscribe(subscriber);

        // Then
        subscriber.assertValue(expected);
    }

    private MeasureJniInvocationUseCase.Result buildExpectedResult() {
        List<MeasurementResult> results = asList(
                buildMeasurementResult(10),
                buildMeasurementResult(100),
                buildMeasurementResult(1000),
                buildMeasurementResult(10000)
        );

        return new MeasureJniInvocationUseCase.Result(
                results,
                results
        );
    }
}