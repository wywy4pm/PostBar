package com.arun.bar.utils;

import android.content.Context;

public class DensityUtils {
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    /**
     * 获取状态栏高度
     *//*
    public static int getStatusHeight(Context context) {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }*/

    public static int getScreenWidth(Context context) {
        int screenWidth = 0;
        if (context != null && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight(Context context) {
        int screenHeight = 0;
        if (context != null && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return screenHeight;
    }

    public static float getScreenDensity(Context context) {
        float density = 0;
        if (context != null && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }
}
