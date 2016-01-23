package com.example.mythbusters.app;

import java.io.Serializable;

/**
 * Default (working) implementation of {@link Presenter}.
 */
public abstract class AbstractPresenter implements Presenter {

    @Override
    public void resume() {
        // Do nothing
    }

    @Override
    public void pause() {
        // Do nothing
    }

    @Override
    public Serializable getState() {
        return null;
    }

}
