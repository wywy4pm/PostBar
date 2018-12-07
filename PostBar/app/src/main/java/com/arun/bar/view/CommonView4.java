package com.arun.bar.view;

/**
 * Created by wy on 2017/6/7.
 */

public interface CommonView4<T> extends MvpView {
    void refresh(T data);

    void refreshMore(T data);

    void refresh(int type, Object data);
}
