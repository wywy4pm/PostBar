package com.arun.bar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.arun.bar.bean.BarBean;
import com.arun.bar.common.Constant;
import com.arun.bar.presenter.InvitePresenter;
import com.arun.bar.utils.SharedPreferencesUtils;
import com.arun.bar.view.CommonView2;

public class InviteActivity extends BaseActivity implements CommonView2<BarBean> {

    private EditText inviteCodeEdit;
    private InvitePresenter invitePresenter;

    public static void jumpToInviteForResult(Context context, int requestCode) {
        Intent intent = new Intent(context, InviteActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();
        initData();
    }

    private void initView() {
        inviteCodeEdit = findViewById(R.id.inviteCodeEdit);
    }

    private void initData() {
        invitePresenter = new InvitePresenter();
        invitePresenter.attachView(this);
    }

    public void joinBarConfirm(View view) {
        if (!TextUtils.isEmpty(inviteCodeEdit.getText())) {
            String inviteCode = inviteCodeEdit.getText().toString();
            if (invitePresenter != null) {
                invitePresenter.joinBar(getUserId(), inviteCode);
            }
        } else {
            showToast(R.string.put_invite_tips);
        }
    }

    @Override
    public void refresh(BarBean data) {
        if (data != null) {
            String barName = data.bar_name;
            String barId = data.uuid;
            if (!TextUtils.isEmpty(barId) && !TextUtils.isEmpty(barName)) {
                showToast(R.string.tips_bar_join_success);
                SharedPreferencesUtils.setConfigString(this, SharedPreferencesUtils.KEY_USER_BAR_ID, barId);
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_BAR_NAME, barName);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }
}
