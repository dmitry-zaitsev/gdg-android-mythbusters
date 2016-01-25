package com.example.mythbusters.app.benchmark.jni.ui;

import com.example.mythbusters.app.platform.PlatformTransformer;
import com.example.mythbusters.app.ui.AbstractPresenter;
import com.example.mythbusters.core.benchmark.jni.MeasureJniInvocationUseCase;
import com.example.mythbusters.domain.measurement.MeasurementMath;

import rx.subscriptions.CompositeSubscription;

/**
 * Presents results of JNI vs JVM benchmark
 */
public class JniBenchmarkPresenter extends AbstractPresenter {

    private final MeasureJniInvocationUseCase measureJniInvocation;

    private final JniBenchmarkView view;

    private final PlatformTransformer platformTransformer;

    private CompositeSubscription subscription;

    public JniBenchmarkPresenter(MeasureJniInvocationUseCase measureJniInvocation,
                                 JniBenchmarkView view,
                                 PlatformTransformer platformTransformer) {
        this.measureJniInvocation = measureJniInvocation;
        this.view = view;
        this.platformTransformer = platformTransformer;
    }

    @Override
    public void resume() {
        super.resume();

        view.showInProgress();

        subscription = new CompositeSubscription();
        subscription.add(
                measureJniInvocation.measure()
                        .map(this::toViewModel)
                        .compose(platformTransformer.newTransformer())
                        .subscribe(viewModel -> {
                            view.setResult(viewModel);
                            view.hideInProgress();
                        })
        );
    }

    private BenchmarkResultViewModel toViewModel(MeasureJniInvocationUseCase.Result result) {
        final long timePerJniCall = MeasurementMath.averageTimePerIteration(result.jniInvocations);
        final long timePerJvmCall = MeasurementMath.averageTimePerIteration(result.jvmInvocations);

        return new BenchmarkResultViewModel(
                timePerJniCall,
                timePerJvmCall
        );
    }

    @Override
    public void pause() {
        super.pause();
        subscription.unsubscribe();
    }

}
