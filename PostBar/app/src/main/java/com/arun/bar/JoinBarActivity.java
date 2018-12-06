package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
        AddBarActivity.jumpToAddBar(this);
    }

    public void joinBar(View view) {
        InviteActivity.jumpToInvite(this);
    }

    /*private boolean isJoinBar() {
        return false;
    }*/
}
