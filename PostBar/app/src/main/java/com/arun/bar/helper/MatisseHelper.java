package com.arun.bar.helper;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.arun.bar.matisse.Matisse;
import com.arun.bar.matisse.MimeType;
import com.arun.bar.matisse.engine.impl.GlideEngine;

/**
 * Created by wy on 2017/6/27.
 */

public class MatisseHelper {
    public static void startPicturePicker(Activity activity, String mode) {
        Matisse.from(activity)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(400)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .addBackMode(mode)
                .forResult(0);
        //activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
