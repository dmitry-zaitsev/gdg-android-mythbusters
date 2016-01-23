package com.example.mythbusters.app.navigation;

/**
 * Navigates between the screens of the application
 */
public interface Navigator {

    /**
     * Navigates to another screen using given transition object.
     *
     * @param transition meta-information of navigation event
     */
    void navigate(Object transition);

}
