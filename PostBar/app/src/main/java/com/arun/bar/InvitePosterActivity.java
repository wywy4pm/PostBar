package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arun.bar.event.PosterEvent;
import com.arun.bar.helper.SaveImageHelper;
import com.arun.bar.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class InvitePosterActivity extends BaseActivity {
    private RelativeLayout layout_poster;
    private TextView poster_title;
    private ImageView qrCode;
    private String bar_name;
    private Bitmap qrCodeBitmap;

    public static void jumpToInvitePoster(Context context) {
        Intent intent = new Intent(context, InvitePosterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_poster);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    private void initView() {
        layout_poster = findViewById(R.id.layout_poster);
        qrCode = findViewById(R.id.qrCode);
        poster_title = findViewById(R.id.poster_title);

        setBack();
        setTitleName(getString(R.string.invite_new_user));
    }

    private void initData() {
        bar_name = SharedPreferencesUtils.getConfigString(this, SharedPreferencesUtils.KEY_USER_BAR);
        poster_title.setText(getString(R.string.invite_poster_title, bar_name));
        if (qrCodeBitmap != null) {
            qrCode.setImageBitmap(qrCodeBitmap);
        }
    }

    public void savePoster(View view) {
        SaveImageHelper.saveToDisk(layout_poster);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getQRBitmap(PosterEvent posterEvent) {
        if (posterEvent != null && posterEvent.bitmap != null) {
            qrCodeBitmap = posterEvent.bitmap;
            if (qrCode != null) {
                qrCode.setImageBitmap(qrCodeBitmap);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
