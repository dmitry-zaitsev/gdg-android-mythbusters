package com.example.mythbusters.app.benchmark.jni.ui.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mythbusters.R;
import com.example.mythbusters.app.android.Dependencies;
import com.example.mythbusters.app.benchmark.jni.ui.BenchmarkResultViewModel;
import com.example.mythbusters.app.benchmark.jni.ui.JniBenchmarkPresenter;
import com.example.mythbusters.app.benchmark.jni.ui.JniBenchmarkView;
import com.example.mythbusters.app.platform.android.AndroidPlatform;

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

        TextView jniResult;
        TextView jvmResult;
        View progressView;

        BenchmarkView() {
            jniResult = (TextView) findViewById(R.id.jni_result);
            jvmResult = (TextView) findViewById(R.id.jvm_result);
            progressView = findViewById(R.id.progressBar);
        }

        @Override
        public void setResult(BenchmarkResultViewModel result) {
            jniResult.setText(
                    result.nanosecondsPerJniCall + " ns"
            );

            jvmResult.setText(
                    result.nanosecondsPerJvmCall + " ns"
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
