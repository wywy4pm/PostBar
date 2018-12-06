package com.arun.bar.retrofit;

import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpManager {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static Handler handler = new Handler();

    public static void get(Param[] params, String url, final OkHttpCallback okHttpCallback) {
        if (params != null && params.length > 0) {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Param param : params) {
                sb.append(param.key);
                sb.append("=");
                sb.append(param.value);
                sb.append("&");
            }
            url = sb.toString().substring(0, sb.length() - 1);
        }

        Request request = new Request.Builder().url(url).build();
        Call call = RetrofitInit.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okHttpCallback.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public static void post(Param[] params, String url, final OkHttpCallback okHttpCallback) {

        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.length > 0) {

            for (Param param : params) {
                builder.add(param.key, param.value);
            }
        }

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = RetrofitInit.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okHttpCallback.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public static void postJson(String url, String json, final OkHttpCallback okHttpCallback) {
        //申明给服务端传递一个json串
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //发送请求获取响应
        Call call = RetrofitInit.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        okHttpCallback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okHttpCallback.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public interface OkHttpCallback {

        void onError(String error);

        void onSuccess(String response);

    }

    public static class Param {
        String key;
        String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
