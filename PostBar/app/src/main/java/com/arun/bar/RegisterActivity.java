package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.arun.bar.bean.RegisterBodyBean;
import com.arun.bar.presenter.RegisterPresenter;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.utils.Utils;
import com.arun.bar.view.CommonView2;

public class RegisterActivity extends BaseActivity implements CommonView2<RegisterBodyBean> {
    private EditText phoneNumEdit;
    private RegisterPresenter registerPresenter;

    public static void jumpToRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        String uid = SharedPreferencesUtils.getUid(this);
        if (!TextUtils.isEmpty(uid)) {//已注册
            finish();
            JoinBarActivity.jumpToJoinBar(this);
        } else {
            registerPresenter = new RegisterPresenter();
            registerPresenter.attachView(this);
        }
    }

    public void initView() {
        phoneNumEdit = findViewById(R.id.phoneNumEdit);
    }

    public void clickToRegister(View view) {
        String phoneNum = phoneNumEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            showToast(R.string.phone_empty);
        } else if (!Utils.isPhone(phoneNum)) {
            showToast(R.string.phone_error);
        } else {
            if (!isLoading()) {
                if (registerPresenter != null) {
                    registerPresenter.register(phoneNum);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPresenter != null) {
            registerPresenter.detachView();
        }
    }

    /*@Override
    public void onError(int errorType, int errorMsg) {
        super.onError(errorType, errorMsg);
        finish();
        JoinBarActivity.jumpToJoinBar(this);
    }*/

    @Override
    public void refresh(RegisterBodyBean data) {
        //showToast(R.string.register_success);
        if (data != null && !TextUtils.isEmpty(data.uuid)) {
            SharedPreferencesUtils.saveUid(this, data.uuid);
            finish();
            JoinBarActivity.jumpToJoinBar(this);
        }
    }
}
