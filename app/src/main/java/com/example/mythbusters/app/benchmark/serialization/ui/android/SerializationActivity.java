package com.example.mythbusters.app.benchmark.serialization.ui.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.mythbusters.R;
import com.example.mythbusters.app.benchmark.serialization.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.serialization.ui.SerializationBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.serialization.ui.SerializationBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;

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

        TextView serializationResult;
        TextView androidSerializationResult;
        View progressView;

        BenchmarkView() {
            serializationResult = (TextView) findViewById(R.id.serializable_result);
            androidSerializationResult = (TextView) findViewById(R.id.parcelable_result);
            progressView = findViewById(R.id.progressBar);
        }

        @Override
        public void setSerializationResult(BenchmarkResultViewModel result) {
            serializationResult.setText(
                    result.nanosecondsPerOperation * 1e-6 + " ms"
            );
        }

        @Override
        public void setParcelableSerializationResult(BenchmarkResultViewModel result) {
            androidSerializationResult.setText(
                    result.nanosecondsPerOperation * 1e-6 + " ms"
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
