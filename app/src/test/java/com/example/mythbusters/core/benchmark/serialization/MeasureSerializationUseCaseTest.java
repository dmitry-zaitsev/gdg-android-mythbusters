package com.example.mythbusters.core.benchmark.serialization;

import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

public class MeasureSerializationUseCaseTest {

    private static final long SINGLE_MEASUREMENT_MS = 100L;

    @Mock
    Benchmark benchmark;
    @Mock
    Serializer serializer;
    @Mock
    MeasureSerializationUseCase.ObjectFactory objectFactory;

    final Object smallObject = new Object();
    final Object bigObject = new Object();
    private InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doReturn(SINGLE_MEASUREMENT_MS)
                .when(benchmark)
                .measureOperation(any(Runnable.class), anyLong());

        doReturn(new byte[10])
                .when(serializer)
                .serialize(anyObject());

        doReturn(smallObject)
                .when(objectFactory)
                .createSmallObject();

        doReturn(bigObject)
                .when(objectFactory)
                .createBigObject();

        inOrder = inOrder(benchmark, serializer, objectFactory);
    }

    @Test
    public void behavior() throws Exception {
        // Given
        final MeasureSerializationUseCase useCase = new MeasureSerializationUseCase(
                benchmark,
                serializer,
                objectFactory
        );

        // When
        useCase.measure()
                .subscribe();

        // Then
        inOrder.verify(objectFactory).createSmallObject();
        verifyObjectSerialized(smallObject, 10);
        verifyObjectSerialized(smallObject, 100);
        verifyObjectSerialized(smallObject, 1000);
        verifyObjectSerialized(smallObject, 10000);

        inOrder.verify(objectFactory).createBigObject();
        verifyObjectSerialized(bigObject, 10);
        verifyObjectSerialized(bigObject, 100);
        verifyObjectSerialized(bigObject, 1000);
        verifyObjectSerialized(bigObject, 10000);
    }

    private void verifyObjectSerialized(Object object, int iterations) {
        inOrder.verify(benchmark).measureOperation(
                any(Runnable.class),
                iterations
        );
        inOrder.verify(serializer, times(iterations)).serialize(eq(object));
    }

    @Test
    public void output() throws Exception {
        // Given
        final MeasureSerializationUseCase useCase = new MeasureSerializationUseCase(
                benchmark,
                serializer,
                objectFactory
        );

        final TestSubscriber<MeasureSerializationUseCase.Result> subscriber = new TestSubscriber<>();

        MeasureSerializationUseCase.Result expected = buildExpectedResult();

        // When
        useCase.measure()
                .subscribe(subscriber);

        // Then
        subscriber.assertValue(expected);
    }

    private MeasureSerializationUseCase.Result buildExpectedResult() {
        final List<MeasurementResult> results = asList(
                buildMeasurementResult(10),
                buildMeasurementResult(100),
                buildMeasurementResult(1000),
                buildMeasurementResult(10000)
        );

        return new MeasureSerializationUseCase.Result(
                results,
                results
        );
    }

    private MeasurementResult buildMeasurementResult(int iterations) {
        return new MeasurementResult(
                iterations,
                iterations * SINGLE_MEASUREMENT_MS
        );
    }

}