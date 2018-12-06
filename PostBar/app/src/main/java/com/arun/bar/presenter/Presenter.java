package com.arun.bar.presenter;

import com.arun.bar.view.MvpView;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

}
