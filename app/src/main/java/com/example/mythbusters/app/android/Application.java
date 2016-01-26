package com.example.mythbusters.app.android;

/**
 * Entry point into the application
 */
public class Application extends android.app.Application {

    private static Application application;

    /**
     * @return singleton instance of the application.
     */
    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

}
