package com.example.mythbusters.app.benchmark.jni;

import com.example.mythbusters.core.benchmark.jni.MeasureJniInvocationUseCase;

public class Invocation implements MeasureJniInvocationUseCase.Invocation {

    static {
        System.loadLibrary("invocation");
    }

    private final ValueHolder holder = new ValueHolder();

    @Override
    public void runOnJvm() {
        holder.setValue(5, 10);
    }

    @Override
    public void runOnJni() {
        setValue(5, 10);
    }

    native void setValue(int x, int y);

    private static class ValueHolder {

        private int x;
        private int y;

        public void setValue(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
