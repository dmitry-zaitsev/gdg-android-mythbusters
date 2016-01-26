package com.example.mythbusters.app.platform.android;

import com.example.mythbusters.app.platform.PlatformTransformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Creates transformers which adapt observables to Android thread requirements
 */
public class AndroidPlatform implements PlatformTransformer {

    @Override
    public <T> Observable.Transformer<T, T> newTransformer() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
