package com.arun.bar.view;

/**
 * Created by WY on 2017/4/14.
 */
public interface CommonView<T> extends MvpView {
    void refresh(T data);

    void refreshMore(T data);
}
