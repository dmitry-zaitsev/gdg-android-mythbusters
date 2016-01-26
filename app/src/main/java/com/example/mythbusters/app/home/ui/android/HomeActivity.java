package com.example.mythbusters.app.home.ui.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mythbusters.R;
import com.example.mythbusters.app.benchmark.jni.ui.android.JniBenchmarkActivity;
import com.example.mythbusters.app.benchmark.preferences.ui.android.PreferencesBenchmarkActivity;
import com.example.mythbusters.app.benchmark.serialization.ui.android.SerializationActivity;
import com.example.mythbusters.app.home.ui.HomePresenter;
import com.example.mythbusters.app.home.ui.HomeView;
import com.example.mythbusters.app.navigation.Navigator;
import com.example.mythbusters.app.navigation.Transitions;

import static com.example.mythbusters.app.navigation.Transitions.toJniBenchmark;
import static com.example.mythbusters.app.navigation.Transitions.toSerializationBenchmark;
import static com.example.mythbusters.app.navigation.Transitions.toSharedPreferencesBenchmark;

public class HomeActivity extends AppCompatActivity {

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homePresenter = new HomePresenter(
                new HomeNavigator(),
                new HomeViewImpl()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        homePresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        homePresenter.pause();
    }

    private class HomeNavigator implements Navigator {

        @Override
        public void navigate(Object transition) {
            if (transition.equals(toSerializationBenchmark())) {
                startActivity(
                        SerializationActivity.newIntent(HomeActivity.this)
                );
            } else if (transition.equals(toSharedPreferencesBenchmark())) {
                startActivity(
                        PreferencesBenchmarkActivity.newIntent(HomeActivity.this)
                );
            } else if (transition.equals(toJniBenchmark())) {
                startActivity(
                        JniBenchmarkActivity.newIntent(HomeActivity.this)
                );
            }
        }

    }

    private class HomeViewImpl implements HomeView {

        private Listener listener;

        public HomeViewImpl() {
            findViewById(R.id.serialization).setOnClickListener(
                    v -> listener.openSerializationBenchmark()
            );

            findViewById(R.id.preferences).setOnClickListener(
                    v -> listener.openSharedPreferencesBenchmark()
            );

            findViewById(R.id.jni).setOnClickListener(
                    v -> listener.openJniBenchmark()
            );
        }

        @Override
        public void setListener(Listener listener) {
            this.listener = listener;
        }

    }

}
