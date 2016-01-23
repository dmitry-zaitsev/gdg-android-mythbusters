package com.example.mythbusters.app.home;

import com.example.mythbusters.app.AbstractPresenter;
import com.example.mythbusters.app.navigation.Navigator;
import com.example.mythbusters.app.navigation.Transitions;

/**
 * Presenter for the home screen of the application
 */
public class HomePresenter extends AbstractPresenter implements HomeView.Listener {

    private final Navigator navigator;
    private final HomeView homeView;

    public HomePresenter(Navigator navigator, HomeView homeView) {
        this.navigator = navigator;
        this.homeView = homeView;

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
