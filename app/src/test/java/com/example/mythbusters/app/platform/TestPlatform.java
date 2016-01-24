package com.example.mythbusters.app.platform;

import rx.Observable;

/**
 * Does no transformation of given observable, returning them as is.
 */
public class TestPlatform implements PlatformTransformer {

    @Override
    public <T> Observable.Transformer<T, T> newTransformer() {
        return observable -> observable;
    }

}
