package com.arun.bar.retrofit;

import com.arun.bar.common.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {

    private static OkHttpClient client;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .client(getClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static OkHttpClient getClient() {
        if (client == null) {
            /*Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String appVersion = "";
                    String deviceModel = "";
                    String osVersion = "";
                    String isRelease = "";
                    String osType = "android";
                    String deviceId = "";
                    String uid = "";
                    String apiVersion = "";
                    if (getAppBean() != null) {
                        appVersion = getAppBean().appVersion;
                        deviceModel = getAppBean().deviceModel;
                        osVersion = getAppBean().osVersion;
                        isRelease = String.valueOf(getAppBean().isRelease);
                        osType = getAppBean().osType;
                        deviceId = getAppBean().deviceId;
                        uid = getAppBean().uid;
                        apiVersion = getAppBean().apiVersion;
                    }
                    Request originalRequest = chain.request();
                    Request authorised = originalRequest.newBuilder()
                            .addHeader("appVersion", appVersion)
                            .addHeader("deviceModel", deviceModel)
                            .addHeader("osVersion", osVersion)
                            .addHeader("isRelease", isRelease)
                            .addHeader("osType", osType)
                            .addHeader("deviceId", deviceId)
                            .addHeader("uid", uid)
                            .addHeader("apiVersion", apiVersion)
                            .build();
                    return chain.proceed(authorised);
                }
            };*/

            client = new OkHttpClient
                    .Builder()
                    //.addInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    private RetrofitInit() {

    }

    private static RetrofitApi retrofitApi;

    public static RetrofitApi getApi() {
        if (retrofitApi == null) {
            retrofitApi = retrofit.create(RetrofitApi.class);
        }
        return retrofitApi;
    }

    public static <T> T createApi(Class<T> mClass) {
        return retrofit.create(mClass);
    }

}
