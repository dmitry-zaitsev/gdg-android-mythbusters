package com.example.mythbusters.app.benchmark.preferences.ui;

import com.example.mythbusters.app.ui.ProgressView;

/**
 * Displays results of {@link android.content.SharedPreferences} benchmark.
 */
public interface PreferencesBenchmarkView extends ProgressView {

    /**
     * @param result result of {@link android.content.SharedPreferences} benchmark.
     */
    void setResult(BenchmarkResultViewModel result);

}
