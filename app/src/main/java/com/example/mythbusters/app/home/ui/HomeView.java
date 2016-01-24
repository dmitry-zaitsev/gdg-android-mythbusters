package com.example.mythbusters.app.home.ui;

/**
 * Home screen of the application
 */
public interface HomeView {

    /**
     * @param listener which will receive callbacks from user interaction
     */
    void setListener(Listener listener);

    /**
     * Receives callbacks from user interaction
     */
    interface Listener {

        /**
         * User wants to see SharedPreferences benchmark results
         */
        void openSharedPreferencesBenchmark();

        /**
         * User wants to see Parcelable vs Serializable benchmark results
         */
        void openSerializationBenchmark();

        /**
         * User wants to see JNI vs JVM invocation benchmark results
         */
        void openJniBenchmark();

    }

}
