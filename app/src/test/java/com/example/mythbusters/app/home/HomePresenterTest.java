package com.example.mythbusters.app.home;

import com.example.mythbusters.app.navigation.Navigator;
import com.example.mythbusters.app.navigation.Transitions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class HomePresenterTest {

    @Mock
    Navigator navigator;
    @Mock
    HomeView view;

    HomePresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new HomePresenter(navigator, view);
    }

    @Test
    public void setListener() throws Exception {
        // When
        HomePresenter presenter = new HomePresenter(navigator, view);

        // Then
        verify(view).setListener(eq(presenter));
    }

    @Test
    public void openSharedPreferencesBenchmark() throws Exception {
        // Given
        presenter.resume();

        // When
        presenter.openSharedPreferencesBenchmark();

        // Then
        verify(navigator).navigate(
                Transitions.toSharedPreferencesBenchmark()
        );
    }

    @Test
    public void openSerializationBenchmark() throws Exception {
        // Given
        presenter.resume();

        // When
        presenter.openSerializationBenchmark();

        // Then
        verify(navigator).navigate(
                Transitions.toSerializationBenchmark()
        );
    }

    @Test
    public void openJniBenchmark() throws Exception {
        // Given
        presenter.resume();

        // When
        presenter.openJniBenchmark();

        // Then
        verify(navigator).navigate(
                Transitions.toJniBenchmark()
        );
    }
}