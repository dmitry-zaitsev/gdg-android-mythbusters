package com.example.mythbusters.app.benchmark.jni.ui.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mythbusters.R;
import com.example.mythbusters.app.benchmark.jni.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.jni.ui.JniBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.jni.ui.JniBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;
import com.example.mythbusters.app.ui.android.ChartRenderer;
import com.github.mikephil.charting.charts.BarChart;

import static com.example.mythbusters.app.android.Dependencies.measureJniInvocationUseCase;

public class JniBenchmarkActivity extends AppCompatActivity {

    private JniBenchmarkPresenter presenter;

    public static Intent newIntent(Context context) {
        return new Intent(context, JniBenchmarkActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_benchmark);

        presenter = new JniBenchmarkPresenter(
                measureJniInvocationUseCase(),
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

    private class BenchmarkView implements JniBenchmarkView {

        final View progressView;
        final ChartRenderer chartRenderer;

        BenchmarkView() {
            progressView = findViewById(R.id.progressBar);

            chartRenderer = new ChartRenderer(
                    (BarChart) findViewById(R.id.chart),
                    value -> (int) value + " ns",
                    "Java",
                    "JNI"
            );
        }

        @Override
        public void setResult(BenchmarkResultViewModel result) {
            chartRenderer.renderValues(
                    result.nanosecondsPerJvmCall,
                    result.nanosecondsPerJniCall
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
