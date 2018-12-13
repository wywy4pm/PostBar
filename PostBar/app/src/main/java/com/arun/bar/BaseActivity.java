package com.arun.bar;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arun.bar.utils.DensityUtils;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.utils.ToastUtils;
import com.arun.bar.view.MvpView;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    public ImageView image_back;
    public TextView titleView;
    public String userId;
    public String deviceId;
    public int screenWidth;
    public ProgressBar progressBar;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = DensityUtils.getScreenWidth(this);
    }

    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initCommonView();
    }

    /*public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        if (view instanceof ConstraintLayout) {
            initProgressBar((ConstraintLayout) view);
        }
        super.setContentView(view);
    }*/

    /*private void initProgressBar(ConstraintLayout constraintLayout) {
        progressBar = new ProgressBar(this);
        //progressBar.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        //progressBar.setLayoutParams(params);
        constraintLayout.addView(progressBar, params);
    }*/

    public void initCommonView() {
        initProgressBar();
        initTitle();
    }

    private void initTitle() {
        titleView = findViewById(R.id.title_name);
    }

    public void setTitleName(String titleName) {
        if (titleView != null && !TextUtils.isEmpty(titleName)) {
            titleView.setText(titleName);
        }
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

    @Override
    public void onLoadStart() {
        setLoading(true);
        showProgressBar();
    }

    @Override
    public void onError(int errorType, int errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void onError(int errorType, String errorMsg) {

    }

    @Override
    public void onRefreshComplete() {
        setLoading(false);
        hideProgressBar();
    }

    public void showToast(int msgRes) {
        ToastUtils.getInstance(this).showToast(msgRes);
    }

    public void showToast(String msg) {
        ToastUtils.getInstance(this).showToast(msg);
    }

    public String getUserId() {
        if (TextUtils.isEmpty(userId)) {
            userId = SharedPreferencesUtils.getUid(this);
        }
        return userId;
    }

    public void setBack(View backView) {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setBack() {
        image_back = findViewById(R.id.image_back);
        if (image_back != null) {
            image_back.setVisibility(View.VISIBLE);
            setBack(image_back);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance(this).destory();
    }
}
