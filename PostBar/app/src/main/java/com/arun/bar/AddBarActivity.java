package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.arun.bar.presenter.BarPresenter;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.view.CommonView2;

public class AddBarActivity extends BaseActivity implements CommonView2 {
    private EditText barNameEdit;
    private EditText nickNameEdit;
    private BarPresenter barPresenter;
    private String barName;

    public static void jumpToAddBar(Context context) {
        Intent intent = new Intent(context, AddBarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bar);
        initView();
        barPresenter = new BarPresenter();
        barPresenter.attachView(this);
    }

    private void initView() {
        barNameEdit = findViewById(R.id.barNameEdit);
        nickNameEdit = findViewById(R.id.nickNameEdit);
    }

    public void addBarConfirm(View view) {
        if (TextUtils.isEmpty(barNameEdit.getText())) {
            showToast(R.string.tips_put_bar_name);
        } /*else if (TextUtils.isEmpty(nickNameEdit.getText())) {
            showToast(R.string.tips_put_nick_name);
        }*/ else {
            if (barPresenter != null) {
                barName = barNameEdit.getText().toString();
                barPresenter.barAdd(barName, getUserId());
            }
        }
    }

    @Override
    public void refresh(Object data) {
        showToast(R.string.tips_bar_add_success);
        SharedPreferencesUtils.setConfigString(this, SharedPreferencesUtils.KEY_USER_BAR, barName);
        finish();
        MainActivity.jumpToMain(this, barName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barPresenter != null) {
            barPresenter.detachView();
        }
    }
}
