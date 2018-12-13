package com.arun.bar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.arun.bar.common.Constant;
import com.arun.bar.presenter.BarPresenter;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.view.CommonView2;
import com.arun.bar.view.CommonView3;

public class AddBarActivity extends BaseActivity implements CommonView3 {
    private EditText barNameEdit;
    private EditText nickNameEdit;
    private BarPresenter barPresenter;
    private String barName;
    private String nickName;
    private boolean isBarNameSuccess;
    private boolean isNickNameSuccess;
    private String bar_uuid;

    public static void jumpToAddBarForResult(Context context, int requestCode) {
        Intent intent = new Intent(context, AddBarActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
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
        } else if (TextUtils.isEmpty(nickNameEdit.getText())) {
            showToast(R.string.tips_put_nick_name);
        } else {
            if (barPresenter != null) {
                barName = barNameEdit.getText().toString();
                nickName = nickNameEdit.getText().toString();
                barPresenter.barAdd(barName, getUserId());
                barPresenter.userUpdate(getUserId(), nickName);
            }
        }
    }

    @Override
    public void refresh(int type, Object data) {
        if (type == BarPresenter.TYPE_BAR_ADD) {
            isBarNameSuccess = true;
            if (data instanceof String) {
                bar_uuid = (String) data;
            }
            SharedPreferencesUtils.setConfigString(this, SharedPreferencesUtils.KEY_USER_BAR, barName);
        } else if (type == BarPresenter.TYPE_USER_UPDATE) {
            isNickNameSuccess = true;
            SharedPreferencesUtils.setConfigString(this, SharedPreferencesUtils.KEY_USER_NICK_NAME, nickName);
        }
        Log.d("TAG", "AddBar refresh isBarNameSuccess = " + isBarNameSuccess + "  isNickNameSuccess = " + isNickNameSuccess);
        if (isBarNameSuccess && isNickNameSuccess) {
            showToast(R.string.tips_bar_add_success);
            if (!TextUtils.isEmpty(bar_uuid)) {
                SharedPreferencesUtils.setConfigString(this, SharedPreferencesUtils.KEY_USER_BAR_ID, bar_uuid);
            }
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_BAR_NAME, barName);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barPresenter != null) {
            barPresenter.detachView();
        }
    }
}
