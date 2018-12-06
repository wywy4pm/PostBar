package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InviteActivity extends AppCompatActivity {

    public static void jumpToInvite(Context context) {
        Intent intent = new Intent(context, InviteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
    }

    public void joinBarConfirm(View view) {

    }
}
