package com.example.mythbusters.app.benchmark.preferences.ui.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mythbusters.R;
import com.example.mythbusters.app.android.Dependencies;
import com.example.mythbusters.app.benchmark.preferences.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.preferences.ui.PreferencesBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.preferences.ui.PreferencesBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;

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

        TextView writeResult;
        TextView readResult;
        View progressView;

        BenchmarkView() {
            writeResult = (TextView) findViewById(R.id.write_result);
            readResult = (TextView) findViewById(R.id.read_result);
            progressView = findViewById(R.id.progressBar);
        }

        @Override
        public void setResult(BenchmarkResultViewModel result) {
            writeResult.setText(
                    result.nanosecondsPerWrite * 1e-6 + " ms"
            );

            readResult.setText(
                    result.nanosecondsPerRead * 1e-6 + " ms"
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
