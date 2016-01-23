package com.example.mythbusters.app;

import java.io.Serializable;

/**
 * Presents content to the client
 */
public interface Presenter {

    /**
     * Signals to presenter that client is ready to consume the data.
     */
    void resume();

    /**
     * Signals to presenter that client is not longer able to consume the data.
     */
    void pause();

    /**
     * @return current state of the presenter.
     */
    Serializable getState();

}
