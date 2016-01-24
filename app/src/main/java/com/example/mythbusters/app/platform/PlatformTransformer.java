package com.example.mythbusters.app.platform;

import rx.Observable;

/**
 * Transforms {@link Observable} to fit it to platform requirements, such as
 * threads on which subscription is done, threads on which values are observed, etc.
 */
public interface PlatformTransformer {

    /**
     * @return new transformer
     */
    <T> Observable.Transformer<T, T> newTransformer();

}
