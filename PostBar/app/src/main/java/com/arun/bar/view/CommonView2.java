package com.arun.bar.view;

/**
 * Created by WY on 2017/5/11.
 */
public interface CommonView2<T> extends MvpView{
    void refresh(T data);
}
