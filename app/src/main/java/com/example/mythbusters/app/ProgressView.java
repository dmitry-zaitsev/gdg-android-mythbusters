package com.example.mythbusters.app;

/**
 * View which is capable of displaying the running progress.
 */
public interface ProgressView {

    /**
     * Displays running progress
     */
    void showInProgress();

    /**
     * Hides running progress
     */
    void hideInProgress();

}
