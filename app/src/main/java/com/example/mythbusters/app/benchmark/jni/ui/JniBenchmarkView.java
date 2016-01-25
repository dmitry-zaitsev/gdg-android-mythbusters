package com.example.mythbusters.app.benchmark.jni.ui;

import com.example.mythbusters.app.ui.ProgressView;

/**
 * Displays difference in speed between JNI and JVM invocations.
 */
public interface JniBenchmarkView extends ProgressView {

    /**
     * @param result result of benchmark
     */
    void setResult(BenchmarkResultViewModel result);

}
