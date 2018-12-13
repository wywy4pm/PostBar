package com.arun.bar;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

/**
 * Created by WY on 2017/5/14.
 */
public class MMApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static MMApplication application;
    //OSS的Bucket
    public static final String OSS_BUCKET_NAME = "85mm";

    //oss上传图片目录
    public static final String OSS_UPLOAD_IMAGE_FOLDER = "postbar/";

    public static final String IMAGE_URL_BASE = "http://resources.link365.cn/";

    //设置OSS数据中心域名或者cname域名
    public static final String OSS_BUCKET_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com/";
    //Key
    private static final String ACCESS_KEY_ID = "LTAI2NTBH0TVhoph";
    private static final String ACCESS_KEY_SECRET = "CF0bPVfcbFYY8SJqRUwHS4WBqMugrZ";
    public static OSS oss;

    public int count = 0;
    //private EventStatisticsHelper helper;

    /*{
        PlatformConfig.setWeixin("wxaa1d1954f46301df", "979486cc1c9736c83f974421282c753e");
        PlatformConfig.setQQZone("1106153512", "sKwAAUJpmHZPVDN2");
        PlatformConfig.setSinaWeibo("3732105978", "6aebd8c81c878394c19f98cde6be4da7", "http://sns.whalecloud.com/sina2/callback");
    }*/

    public static MMApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //getCurrentServer();
        //Config.DEBUG = true;
        //UMShareAPI.get(this);
        /*AppHelper.getInstance().setAppConfig(getApplicationContext());
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.setDebugMode(false);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                if (TextUtils.isEmpty(SharedPreferencesUtils.getConfigString(
                        getApplicationContext(), SharedPreferencesUtils.KEY_DEVICE_TOKEN))) {
                    SharedPreferencesUtils.setConfigString(getApplicationContext(), SharedPreferencesUtils.KEY_DEVICE_TOKEN, deviceToken);

                    new DeviceTokenPresenter(getApplicationContext())
                            .postDeviceToken(deviceToken);
                }
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        helper = new EventStatisticsHelper(getApplicationContext());

        PushHelper.setPushNotification(mPushAgent, helper);*/
        //mPushAgent.setPushIntentServiceClass(PushIntentService.class);
        //初始化OSS配置
        initOSSConfig();

        registerActivityLifecycleCallbacks(this);
    }

    /*private void getCurrentServer() {
        int currentServer = SharedPreferencesUtils.getConfigInt(this, SharedPreferencesUtils.KEY_SERVER);
        if (currentServer == Constant.SERVER_TYPE_TEST) {
            Constant.API_BASE_URL = Constant.BASE_URL_TEST;
            Constant.WEB_BASE_URL = Constant.BASE_URL_TEST;
        } else {
            Constant.API_BASE_URL = Constant.BASE_URL_PROD;
            Constant.WEB_BASE_URL = Constant.BASE_URL_PROD;
        }
    }*/

    private void initOSSConfig() {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if (BuildConfig.DEBUG) {
            OSSLog.enableLog();
        }
        oss = new OSSClient(getApplicationContext(), OSS_BUCKET_ENDPOINT, credentialProvider, conf);
        //Log.d("TAG", "OSSClient = " + oss);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.v("viclee", activity + "onActivityCreated");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.v("viclee", activity + "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.v("viclee", activity + "onActivityStarted");
        if (count == 0) {
            Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
            /*if (helper != null) {
                helper.recordUserAction(getApplicationContext(), EventConstant.APP_FRONT);
            }*/
        }
        count++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.v("viclee", activity + "onActivityStopped");
        count--;
        if (count == 0) {
            Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
            /*if (helper != null) {
                helper.recordUserAction(getApplicationContext(), EventConstant.APP_BACK);
            }*/
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.v("viclee", activity + "onActivityPaused");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.v("viclee", activity + "onActivityResumed");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.v("viclee", activity + "onActivityDestroyed");
    }


    /**
     * 程序终止
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        /*if (helper != null) {
            helper.detachView();
        }*/
    }
}
