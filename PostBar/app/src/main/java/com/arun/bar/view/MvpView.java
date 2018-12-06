package com.arun.bar.view;

import android.support.annotation.StringRes;

import io.reactivex.disposables.Disposable;

public interface MvpView {
    void onLoadStart();

    void onError(int errorType, @StringRes int errorMsg);

    void onError(int errorType, String errorMsg);

    void onRefreshComplete();
}
