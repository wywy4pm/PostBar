package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.arun.bar.event.PosterEvent;
import com.arun.bar.retrofit.RetrofitInit;
import com.arun.bar.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitePriceActivity extends BaseActivity {

    private EditText invite_price;
    private String bar_uuid;

    public static void jumpToInvitePrice(Context context) {
        Intent intent = new Intent(context, InvitePriceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_price);
        initView();
        initData();
    }

    private void initView() {
        invite_price = findViewById(R.id.invite_price);
        setBack();
        setTitleName(getString(R.string.invite_new_user));
    }

    private void initData() {
        bar_uuid = SharedPreferencesUtils.getConfigString(this, SharedPreferencesUtils.KEY_USER_BAR_ID);
    }

    public void getCode(String joinBarFee) {
        showProgressBar();
        Call<ResponseBody> call = RetrofitInit.getApi().getInviteQRCode(joinBarFee, bar_uuid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    InputStream is = null;
                    try {
                        is = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                        hideProgressBar();
                        InvitePosterActivity.jumpToInvitePoster(InvitePriceActivity.this);
                        EventBus.getDefault().postSticky(new PosterEvent(bitmap));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("TAG", "onFailure msg = " + t.getMessage());
            }
        });
    }

    public void createPoster(View view) {
        if (!TextUtils.isEmpty(invite_price.getText())) {
            getCode(invite_price.getText().toString());
        } else {
            showToast(R.string.invite_save_tips);
        }
    }
}
