package com.example.mythbusters.app.home.ui;

import com.example.mythbusters.app.AbstractPresenter;
import com.example.mythbusters.app.navigation.Navigator;
import com.example.mythbusters.app.navigation.Transitions;

/**
 * Presenter for the home screen of the application
 */
public class HomePresenter extends AbstractPresenter implements HomeView.Listener {

    private final Navigator navigator;

    public HomePresenter(Navigator navigator, HomeView homeView) {
        this.navigator = navigator;

        homeView.setListener(this);
    }

    @Override
    public void openSharedPreferencesBenchmark() {
        navigator.navigate(
                Transitions.toSharedPreferencesBenchmark()
        );
    }

    @Override
    public void openSerializationBenchmark() {
        navigator.navigate(
                Transitions.toSerializationBenchmark()
        );
    }

    @Override
    public void openJniBenchmark() {
        navigator.navigate(
                Transitions.toJniBenchmark()
        );
    }

}
