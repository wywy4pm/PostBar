package com.arun.bar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arun.bar.common.Constant;
import com.arun.bar.utils.SharedPreferencesUtils;

public class JoinBarActivity extends BaseActivity {
    private TextView add_bar;
    private TextView join_bar;
    //private ProgressBar progressBar;

    public static void jumpToJoinBar(Context context) {
        Intent intent = new Intent(context, JoinBarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_bar);
        initView();
        initData();
        String barName = SharedPreferencesUtils.getConfigString(this, SharedPreferencesUtils.KEY_USER_BAR);
        if (!TextUtils.isEmpty(barName)) {//已新建吧
            finish();
            MainActivity.jumpToMain(this, barName);
        }
    }

    public void initView() {
        add_bar = findViewById(R.id.add_bar);
        join_bar = findViewById(R.id.join_bar);
        showProgressBar();
    }

    private void initData() {
        //if (!isJoinBar()) {
        showBtn();
        //}
    }

    private void showBtn() {
        add_bar.setVisibility(View.VISIBLE);
        join_bar.setVisibility(View.VISIBLE);
        hideProgressBar();
    }

    public void addBar(View view) {
        AddBarActivity.jumpToAddBarForResult(this, Constant.REQUEST_CODE_ADD_BAR);
    }

    public void joinBar(View view) {
        InviteActivity.jumpToInviteForResult(this, Constant.REQUEST_CODE_ADD_BAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "JoinBar onActivityResult requestCode = " + requestCode);
        if (requestCode == Constant.REQUEST_CODE_ADD_BAR) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
                if (data != null && data.getExtras() != null && data.getExtras().containsKey(Constant.INTENT_BAR_NAME)) {
                    String barName = data.getExtras().getString(Constant.INTENT_BAR_NAME);
                    MainActivity.jumpToMain(this, barName);
                }
            }
        }
    }

    /*private boolean isJoinBar() {
        return false;
    }*/
}
