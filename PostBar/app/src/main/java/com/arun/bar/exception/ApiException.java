package com.arun.bar.exception;

import android.support.annotation.StringRes;

/**
 * Created by wy on 2017/7/5.
 */

public class ApiException extends Exception {
    //错误码
    public int code;

    public String message;

    //错误信息
    @StringRes
    public int errorMsg;

    //错误类型
    public Throwable throwable;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.throwable = throwable;
    }

    public void showError() {
        //ToastUtils.showText(MallApp.getInstance(), message);
    }
}
