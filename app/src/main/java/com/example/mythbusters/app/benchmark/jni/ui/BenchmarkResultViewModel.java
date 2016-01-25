package com.example.mythbusters.app.benchmark.jni.ui;

/**
 * Result of JNI vs JVM invocation benchmark
 */
public class BenchmarkResultViewModel {

    /**
     * Nanoseconds it takes to call JNI method
     */
    public final long nanosecondsPerJniCall;

    /**
     * Nanoseconds it takes to call JVM method
     */
    public final long nanosecondsPerJvmCall;

    public BenchmarkResultViewModel(long nanosecondsPerJniCall, long nanosecondsPerJvmCall) {
        this.nanosecondsPerJniCall = nanosecondsPerJniCall;
        this.nanosecondsPerJvmCall = nanosecondsPerJvmCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenchmarkResultViewModel that = (BenchmarkResultViewModel) o;

        return nanosecondsPerJniCall == that.nanosecondsPerJniCall
                && nanosecondsPerJvmCall == that.nanosecondsPerJvmCall;

    }

    @Override
    public int hashCode() {
        int result = (int) (nanosecondsPerJniCall ^ (nanosecondsPerJniCall >>> 32));
        result = 31 * result + (int) (nanosecondsPerJvmCall ^ (nanosecondsPerJvmCall >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BenchmarkResultViewModel{" +
                "nanosecondsPerJniCall=" + nanosecondsPerJniCall +
                ", nanosecondsPerJvmCall=" + nanosecondsPerJvmCall +
                '}';
    }

}
