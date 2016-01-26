package com.example.mythbusters.app.android;

import android.preference.PreferenceManager;

import com.example.mythbusters.app.benchmark.jni.Invocation;
import com.example.mythbusters.app.benchmark.preferences.SharedPreferencesStorage;
import com.example.mythbusters.app.benchmark.preferences.ValueProvider;
import com.example.mythbusters.app.benchmark.serialization.AndroidParcelableSerializer;
import com.example.mythbusters.app.benchmark.serialization.JavaSerializer;
import com.example.mythbusters.app.benchmark.serialization.ObjectFactory;
import com.example.mythbusters.core.benchmark.Benchmark;
import com.example.mythbusters.core.benchmark.io.MeasureWriteReadUseCase;
import com.example.mythbusters.core.benchmark.jni.MeasureJniInvocationUseCase;
import com.example.mythbusters.core.benchmark.serialization.MeasureSerializationUseCase;
import com.example.mythbusters.domain.measurement.TimerBenchmark;

import static com.example.mythbusters.domain.measurement.WarmupBenchmark.warmUp;

/**
 * Provides dependencies of the application
 */
public class Dependencies {

    private static final MeasureSerializationUseCase measureAndroidSerializationUseCase = new MeasureSerializationUseCase(
            warmUp(benchmark()),
            new AndroidParcelableSerializer(),
            new ObjectFactory()
    );

    private static final MeasureSerializationUseCase measureJavaSerializationUseCase = new MeasureSerializationUseCase(
            warmUp(benchmark()),
            new JavaSerializer(),
            new ObjectFactory()
    );

    private static MeasureWriteReadUseCase measureSharedPreferencesUseCase;

    private static final MeasureJniInvocationUseCase measureJniInvocationUseCase = new MeasureJniInvocationUseCase(
            warmUp(benchmark(), 100000),
            new Invocation()
    );

    public static MeasureSerializationUseCase measureAndroidSerializationUseCase() {
        return measureAndroidSerializationUseCase;
    }

    public static MeasureSerializationUseCase measureJavaSerializationUseCase() {
        return measureJavaSerializationUseCase;
    }

    public static MeasureWriteReadUseCase measureSharedPreferencesUseCase() {
        if (measureSharedPreferencesUseCase == null) {
            measureSharedPreferencesUseCase = new MeasureWriteReadUseCase(
                    benchmark(),
                    new SharedPreferencesStorage(
                            PreferenceManager.getDefaultSharedPreferences(
                                    Application.getApplication()
                            )
                    ),
                    new ValueProvider()
            );
        }

        return measureSharedPreferencesUseCase;
    }

    public static MeasureJniInvocationUseCase measureJniInvocationUseCase() {
        return measureJniInvocationUseCase;
    }

    public static Benchmark benchmark() {
        return TimerBenchmark.nanoTimer();
    }

}
