package com.example.mythbusters.app.benchmark.serialization.ui.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mythbusters.R;
import com.example.mythbusters.app.benchmark.serialization.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.serialization.ui.SerializationBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.serialization.ui.SerializationBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;
import com.example.mythbusters.app.ui.android.ChartRenderer;
import com.github.mikephil.charting.charts.BarChart;

import java.text.NumberFormat;

import static com.example.mythbusters.app.android.Dependencies.measureAndroidSerializationUseCase;
import static com.example.mythbusters.app.android.Dependencies.measureJavaSerializationUseCase;

public class SerializationActivity extends AppCompatActivity {

    private SerializationBenchmarkPresenter presenter;

    public static Intent newIntent(Context context) {
        return new Intent(context, SerializationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialization);

        presenter = new SerializationBenchmarkPresenter(
                measureJavaSerializationUseCase(),
                measureAndroidSerializationUseCase(),
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

    private class BenchmarkView implements SerializationBenchmarkView {

        final View progressView;
        final ChartRenderer chartRenderer;

        long serializableResult;
        long parcelableResult;

        BenchmarkView() {
            progressView = findViewById(R.id.progressBar);

            final NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(3);

            chartRenderer = new ChartRenderer(
                    (BarChart) findViewById(R.id.chart),
                    value -> numberFormat.format(value * 1e-6) + " ms",
                    "Serializable",
                    "Parcelable"
            );
        }

        @Override
        public void setSerializationResult(BenchmarkResultViewModel result) {
            serializableResult = result.nanosecondsPerOperation;

            refreshValues();
        }

        @Override
        public void setParcelableSerializationResult(BenchmarkResultViewModel result) {
            parcelableResult = result.nanosecondsPerOperation;

            refreshValues();
        }

        private void refreshValues() {
            chartRenderer.renderValues(serializableResult, parcelableResult);
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
