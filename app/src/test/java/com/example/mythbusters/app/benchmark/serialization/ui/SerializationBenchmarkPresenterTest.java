package com.example.mythbusters.app.benchmark.serialization.ui;

import com.example.mythbusters.app.platform.TestPlatform;
import com.example.mythbusters.core.benchmark.MockBenchmark;
import com.example.mythbusters.core.benchmark.serialization.MeasureSerializationUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.example.mythbusters.core.benchmark.BenchmarkUtils.buildMeasurementResult;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static rx.Observable.empty;
import static rx.Observable.just;

public class SerializationBenchmarkPresenterTest {

    @Mock
    MeasureSerializationUseCase javaSerialization;
    @Mock
    MeasureSerializationUseCase androidSerialization;
    @Mock
    SerializationBenchmarkView view;

    SerializationBenchmarkPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new SerializationBenchmarkPresenter(
                javaSerialization,
                androidSerialization,
                view,
                new TestPlatform()
        );
    }

    @Test
    public void noInteractionsBeforeResume() throws Exception {
        // Given
        // Presenter is created, but suspended

        verifyZeroInteractions(javaSerialization, androidSerialization, view);
    }

    @Test
    public void measureAndDisplay() throws Exception {
        // Given
        doReturn(just(buildUseCaseResult()))
                .when(javaSerialization)
                .measure();

        doReturn(just(buildUseCaseResult()))
                .when(androidSerialization)
                .measure();

        // When
        presenter.resume();

        // Then
        InOrder inOrder = inOrder(view);

        inOrder.verify(view).showInProgress();
        inOrder.verify(view).setSerializationResult(
                buildBenchmarkViewModel()
        );
        inOrder.verify(view).setParcelableSerializationResult(
                buildBenchmarkViewModel()
        );
        inOrder.verify(view).hideInProgress();
    }

    @Test
    public void displayProgressWhileNoData() throws Exception {
        // Given
        doReturn(empty())
                .when(javaSerialization)
                .measure();

        doReturn(empty())
                .when(androidSerialization)
                .measure();

        // When
        presenter.resume();

        // Then
        verify(view).showInProgress();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void displayPartialResult() throws Exception {
        // Given
        doReturn(just(buildUseCaseResult()))
                .when(javaSerialization)
                .measure();

        doReturn(empty())
                .when(androidSerialization)
                .measure();

        // When
        presenter.resume();

        // Then
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showInProgress();
        inOrder.verify(view).setSerializationResult(buildBenchmarkViewModel());
        inOrder.verifyNoMoreInteractions();
    }

    private BenchmarkResultViewModel buildBenchmarkViewModel() {
        return new BenchmarkResultViewModel(
                3 * MockBenchmark.MEASUREMENT_MS / 111
        );
    }

    private MeasureSerializationUseCase.Result buildUseCaseResult() {
        return new MeasureSerializationUseCase.Result(
                asList(
                        buildMeasurementResult(1),
                        buildMeasurementResult(10),
                        buildMeasurementResult(100)
                ),
                asList(
                        buildMeasurementResult(1),
                        buildMeasurementResult(10),
                        buildMeasurementResult(100)
                )
        );
    }

}