package com.example.mythbusters.app.navigation;

/**
 * Static factory for transitions between screens
 */
public class Transitions {

    private Transitions() {
    }

    /**
     * Leads to SharedPreferences benchmark screen
     */
    public static String toSharedPreferencesBenchmark() {
        return "SHARED_PREFERENCES_BENCHMARK";
    }

    /**
     * Leads to Parcelable vs Serializable benchmark
     */
    public static String toSerializationBenchmark() {
        return "PARCELABLE_VS_SERIALIZABLE_BENCHMARK";
    }

    /**
     * Leads to JNI vs JVM invocation benchmark
     */
    public static String toJniBenchmark() {
        return "JNI_VS_JVM_INVOCATION_BENCHMARK";
    }

}
