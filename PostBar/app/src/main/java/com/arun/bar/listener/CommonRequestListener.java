package com.arun.bar.listener;

import android.support.annotation.StringRes;

import io.reactivex.disposables.Disposable;

/**
 * Created by wy on 2017/5/22.
 */

public interface CommonRequestListener<T> {
    void onStart(Disposable disposable);

    void onComplete();

    void onSuccess(T data);

    void onError(int errorType, @StringRes int errorMsg);

}
