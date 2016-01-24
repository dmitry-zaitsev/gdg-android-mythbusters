package com.example.mythbusters.app.benchmark.preferences.ui;

import com.example.mythbusters.app.platform.PlatformTransformer;
import com.example.mythbusters.app.ui.AbstractPresenter;
import com.example.mythbusters.core.benchmark.io.MeasureWriteReadUseCase;
import com.example.mythbusters.domain.measurement.MeasurementMath;

import rx.subscriptions.CompositeSubscription;

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
        final long timePerWrite = MeasurementMath.averageTimePerIteration(result.writes);
        final long timePerRead = MeasurementMath.averageTimePerIteration(result.reads);

        return new BenchmarkResultViewModel(
                timePerRead,
                timePerWrite
        );
    }

    @Override
    public void pause() {
        super.pause();
        subscription.unsubscribe();
    }

}
