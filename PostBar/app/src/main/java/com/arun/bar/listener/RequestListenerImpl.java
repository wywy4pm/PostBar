package com.arun.bar.listener;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.presenter.BasePresenter;
import com.arun.bar.view.MvpView;

import io.reactivex.disposables.Disposable;

/**
 * Created by wy on 2017/5/22.
 */

public abstract class RequestListenerImpl implements CommonRequestListener<CommonApiResponse> {
    private MvpView mvpView;
    private BasePresenter basePresenter;

    public RequestListenerImpl(MvpView mvpView, BasePresenter basePresenter) {
        this.mvpView = mvpView;
        this.basePresenter = basePresenter;
    }

    @Override
    public void onStart(Disposable disposable) {
        if (mvpView != null) {
            mvpView.onLoadStart();
        }
        if (basePresenter != null) {
            basePresenter.addDisposable(disposable);
        }
    }

    @Override
    public void onComplete() {
        if (mvpView != null) {
            mvpView.onRefreshComplete();
        }
    }

    @Override
    public void onError(int errorType, int errorMsg) {
        if (mvpView != null) {
            mvpView.onError(errorType, errorMsg);
        }
    }
}
