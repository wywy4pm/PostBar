package com.arun.bar.presenter;

import com.arun.bar.view.MvpView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    //private Context context;
    private List<Disposable> disposables;

    public BasePresenter() {
        //this.context = context;
        disposables = new ArrayList<>();
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        disposables = null;
    }

    /**
     * 添加到列表，在detachView时取消订阅。在子类定义的subscriber需要手动调用添加
     *
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
