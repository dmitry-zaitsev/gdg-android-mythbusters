package com.example.mythbusters.app.benchmark.serialization.ui;

import com.example.mythbusters.app.ProgressView;

/**
 * Shows results of serialization benchmark
 */
public interface SerializationBenchmarkView extends ProgressView {

    /**
     * Displays results of standard Java serialization
     */
    void setSerializationResult(BenchmarkResultViewModel result);

    /**
     * Displays results of Android serialization.
     */
    void setParcelableSerializationResult(BenchmarkResultViewModel result);

}
