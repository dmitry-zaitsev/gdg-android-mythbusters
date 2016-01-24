package com.example.mythbusters.app.benchmark.serialization.ui;

import com.example.mythbusters.app.platform.PlatformTransformer;
import com.example.mythbusters.app.ui.AbstractPresenter;
import com.example.mythbusters.core.benchmark.serialization.MeasureSerializationUseCase;
import com.example.mythbusters.domain.measurement.MeasurementResult;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static rx.Observable.from;

/**
 * Presenter for the screen with results of serialization benchmark
 */
public class SerializationBenchmarkPresenter extends AbstractPresenter {

    private final MeasureSerializationUseCase measureJavaSerialization;
    private final MeasureSerializationUseCase measureAndroidSerialization;

    private final SerializationBenchmarkView view;

    private final PlatformTransformer platformTransformer;

    private CompositeSubscription subscription;

    public SerializationBenchmarkPresenter(MeasureSerializationUseCase measureJavaSerialization,
                                           MeasureSerializationUseCase measureAndroidSerialization,
                                           SerializationBenchmarkView view,
                                           PlatformTransformer platformTransformer) {
        this.measureJavaSerialization = measureJavaSerialization;
        this.measureAndroidSerialization = measureAndroidSerialization;
        this.view = view;
        this.platformTransformer = platformTransformer;
    }

    @Override
    public void resume() {
        super.resume();

        view.showInProgress();

        startMeasurement();
    }

    private void startMeasurement() {
        subscription = new CompositeSubscription();

        final Observable<MeasureSerializationUseCase.Result> javaSerializationMeasurement = measureJavaSerialization
                .measure()
                .cache();

        final Observable<MeasureSerializationUseCase.Result> androidSerializationMeasurement = measureAndroidSerialization
                .measure()
                .cache();

        subscribeToMeasurements(
                javaSerializationMeasurement,
                androidSerializationMeasurement
        );
    }

    private void subscribeToMeasurements(Observable<MeasureSerializationUseCase.Result> javaSerializationMeasurement,
                                         Observable<MeasureSerializationUseCase.Result> androidSerializationMeasurement) {
        subscription.add(
                subscribeToJavaSerializationMeasurement(
                        javaSerializationMeasurement
                )
        );

        subscription.add(
                subscribeToAndroidSerializationResult(
                        androidSerializationMeasurement
                )
        );

        subscription.add(
                subscribeToAllMeasurementCompletion(
                        javaSerializationMeasurement,
                        androidSerializationMeasurement
                )
        );
    }

    private Subscription subscribeToAllMeasurementCompletion(Observable<MeasureSerializationUseCase.Result> javaSerializationMeasurement, Observable<MeasureSerializationUseCase.Result> androidSerializationMeasurement) {
        return Observable.combineLatest(
                javaSerializationMeasurement,
                androidSerializationMeasurement,
                (first, second) -> null
        )
                .compose(platformTransformer.newTransformer())
                .subscribe(r -> view.hideInProgress());
    }

    private Subscription subscribeToAndroidSerializationResult(Observable<MeasureSerializationUseCase.Result> androidSerializationMeasurement) {
        return androidSerializationMeasurement
                .map(result -> toViewModel(result.bigObjectSerialization))
                .compose(platformTransformer.newTransformer())
                .subscribe(view::setParcelableSerializationResult);
    }

    private Subscription subscribeToJavaSerializationMeasurement(Observable<MeasureSerializationUseCase.Result> javaSerializationMeasurement) {
        return javaSerializationMeasurement
                .map(result -> toViewModel(result.bigObjectSerialization))
                .compose(platformTransformer.newTransformer())
                .subscribe(view::setSerializationResult);
    }

    private BenchmarkResultViewModel toViewModel(List<MeasurementResult> measurementResults) {
        final Observable<Long> iterationsSum = from(measurementResults)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.iterations
                );

        return from(measurementResults)
                .reduce(
                        0L,
                        (sum, measurementResult) -> sum + measurementResult.timeMs
                )
                .withLatestFrom(
                        iterationsSum,
                        (totalTime, totalIterations) -> totalTime / totalIterations
                )
                .map(
                        BenchmarkResultViewModel::new
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
