package com.example.mythbusters.app.benchmark.preferences.ui;

import com.example.mythbusters.app.platform.PlatformTransformer;
import com.example.mythbusters.app.ui.AbstractPresenter;
import com.example.mythbusters.core.benchmark.io.MeasureWriteReadUseCase;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static rx.Observable.from;

/**
 * Presenter for {@link android.content.SharedPreferences} benchmark results
 */
public class PreferencesBenchmarkPresenter extends AbstractPresenter {

    private final MeasureWriteReadUseCase measureWriteRead;

    private final PreferencesBenchmarkView view;

    private final PlatformTransformer platformTransformer;

    private CompositeSubscription subscription;

    public PreferencesBenchmarkPresenter(MeasureWriteReadUseCase measureWriteRead,
                                         PreferencesBenchmarkView view,
                                         PlatformTransformer platformTransformer) {
        this.measureWriteRead = measureWriteRead;
        this.view = view;
        this.platformTransformer = platformTransformer;
    }

    @Override
    public void resume() {
        super.resume();

        view.showInProgress();

        startMeasuring();
    }

    private void startMeasuring() {
        subscription = new CompositeSubscription();

        subscription.add(
                measureWriteRead.measure()
                        .map(this::toViewModel)
                        .compose(platformTransformer.newTransformer())
                        .subscribe(viewModel -> {
                            view.setResult(viewModel);
                            view.hideInProgress();
                        })
        );
    }

    private BenchmarkResultViewModel toViewModel(MeasureWriteReadUseCase.Result result) {
        final long timePerWrite = computeTimePerWrite(result);
        final long timePerRead = computeTimePerRead(result);

        return new BenchmarkResultViewModel(
                timePerRead,
                timePerWrite
        );
    }

    private long computeTimePerWrite(MeasureWriteReadUseCase.Result result) {
        final Observable<Long> iterations = from(result.writes)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.iterations
                );

        return from(result.writes)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.timeMs
                )
                .withLatestFrom(
                        iterations,
                        (totalTime, totalIterations) -> totalTime / totalIterations
                )
                .toBlocking()
                .first();
    }

    private long computeTimePerRead(MeasureWriteReadUseCase.Result result) {
        final Observable<Long> iterations = from(result.reads)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.iterations
                );

        return from(result.reads)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.timeMs
                )
                .withLatestFrom(
                        iterations,
                        (totalTime, totalIterations) -> totalTime / totalIterations
                )
                .toBlocking()
                .first();
    }

    @Override
    public void pause() {
        super.pause();
        subscription.unsubscribe();
    }

}
