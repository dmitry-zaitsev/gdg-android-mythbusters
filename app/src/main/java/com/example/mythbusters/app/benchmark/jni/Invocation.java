package com.example.mythbusters.app.benchmark.jni;

import com.example.mythbusters.core.benchmark.jni.MeasureJniInvocationUseCase;

public class Invocation implements MeasureJniInvocationUseCase.Invocation {

    static {
        System.loadLibrary("invocation");
    }

    @Override
    public void runOnJvm() {
        final ValueHolder holder = new ValueHolder();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                holder.setValue(i, j);
            }
        }
    }

    @Override
    public void runOnJni() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                setValue(i, j);
            }
        }
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
