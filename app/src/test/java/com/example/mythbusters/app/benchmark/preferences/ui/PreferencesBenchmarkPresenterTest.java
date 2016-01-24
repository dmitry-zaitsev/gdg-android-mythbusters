package com.example.mythbusters.app.benchmark.preferences.ui;

import com.example.mythbusters.app.platform.TestPlatform;
import com.example.mythbusters.core.benchmark.MockBenchmark;
import com.example.mythbusters.core.benchmark.io.MeasureWriteReadUseCase;

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

public class PreferencesBenchmarkPresenterTest {

    @Mock
    MeasureWriteReadUseCase measureWriteRead;
    @Mock
    PreferencesBenchmarkView view;

    PreferencesBenchmarkPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new PreferencesBenchmarkPresenter(
                measureWriteRead,
                view,
                new TestPlatform()
        );
    }

    @Test
    public void noInteractionBeforeResume() throws Exception {
        // Given
        // Presenter is created, but suspended

        verifyZeroInteractions(measureWriteRead, view);
    }

    @Test
    public void displayProgressWhileNoData() throws Exception {
        // Given
        doReturn(empty())
                .when(measureWriteRead)
                .measure();

        // When
        presenter.resume();

        // Then
        verify(view).showInProgress();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void measureAndDisplay() throws Exception {
        // Given
        doReturn(just(buildUseCaseResult()))
                .when(measureWriteRead)
                .measure();

        // When
        presenter.resume();

        // Then
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showInProgress();
        inOrder.verify(view).setResult(
                buildBenchmarkViewModel()
        );
        inOrder.verify(view).hideInProgress();
    }

    private BenchmarkResultViewModel buildBenchmarkViewModel() {
        return new BenchmarkResultViewModel(
                (long) ((3 * MockBenchmark.MEASUREMENT_MS / 111.0) * 1e3),
                (long) ((3 * MockBenchmark.MEASUREMENT_MS / 111.0) * 1e3)
        );
    }

    private MeasureWriteReadUseCase.Result buildUseCaseResult() {
        return new MeasureWriteReadUseCase.Result(
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