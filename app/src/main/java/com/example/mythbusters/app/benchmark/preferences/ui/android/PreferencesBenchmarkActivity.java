package com.example.mythbusters.app.benchmark.preferences.ui.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mythbusters.R;
import com.example.mythbusters.app.benchmark.preferences.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.preferences.ui.PreferencesBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.preferences.ui.PreferencesBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;
import com.example.mythbusters.app.ui.android.ChartRenderer;
import com.github.mikephil.charting.charts.BarChart;

import java.text.NumberFormat;

import static com.example.mythbusters.app.android.Dependencies.measureSharedPreferencesUseCase;

public class PreferencesBenchmarkActivity extends AppCompatActivity {

    private PreferencesBenchmarkPresenter presenter;

    public static Intent newIntent(Context context) {
        return new Intent(context, PreferencesBenchmarkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_benchmark);

        presenter = new PreferencesBenchmarkPresenter(
                measureSharedPreferencesUseCase(),
                new BenchmarkView(),
                new AndroidPlatform()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.pause();
    }

    private class BenchmarkView implements PreferencesBenchmarkView {

        final View progressView;
        final ChartRenderer chartRenderer;

        BenchmarkView() {
            progressView = findViewById(R.id.progressBar);

            final NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(3);

            chartRenderer = new ChartRenderer(
                    (BarChart) findViewById(R.id.chart),
                    value -> numberFormat.format((value * 1e-6)) + " ms",
                    "Write",
                    "Read"
            );
        }

        @Override
        public void setResult(BenchmarkResultViewModel result) {
            chartRenderer.renderValues(
                    result.nanosecondsPerWrite,
                    result.nanosecondsPerRead
            );
        }

        @Override
        public void showInProgress() {
            progressView.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideInProgress() {
            progressView.setVisibility(View.GONE);
        }

    }

}
