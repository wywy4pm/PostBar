package com.arun.bar.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arun.bar.R;
import com.arun.bar.utils.DensityUtils;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.utils.ToastUtils;
import com.arun.bar.view.MvpView;

public abstract class BaseFragment extends Fragment implements MvpView{
    protected Activity thisInstance;
    protected View rootView;
    private LayoutInflater inflater;
    private int layoutId;
    private View no_network;
    public boolean isLoading;
    public int screenWidth;
    public int screenHeight;
    public String userId;
    public ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            this.inflater = inflater;
            thisInstance = getActivity();
            layoutId = preparedCreate(savedInstanceState);
            int themeId = getTheme();
            if (themeId > 0) {
                final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), themeId);
                inflater = inflater.cloneInContext(contextThemeWrapper);
            }
            rootView = inflater.inflate(layoutId, null);

            initCommon();

            initView();
            initData();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initCommon() {
        screenWidth = DensityUtils.getScreenWidth(getActivity());
        screenHeight = DensityUtils.getScreenHeight(getActivity());
        userId = SharedPreferencesUtils.getUid(getActivity());
        initProgressBar();
    }

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected abstract int preparedCreate(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();

    public int getTheme() {
        return -1;
    }

    @Override
    public void onError(int errorType, int errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onError(int errorType, String errorMsg) {

    }

    @Override
    public void onLoadStart() {
        setLoading(true);
        showProgressBar();
    }

    @Override
    public void onRefreshComplete() {
        setLoading(false);
        hideProgressBar();
    }

    private void initProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void showToast(int msgRes) {
        ToastUtils.getInstance(getActivity()).showToast(msgRes);
    }

    public void showToast(String msg) {
        ToastUtils.getInstance(getActivity()).showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance(getActivity()).destory();
    }
}
