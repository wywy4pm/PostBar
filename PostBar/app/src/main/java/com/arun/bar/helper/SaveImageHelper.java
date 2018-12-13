package com.arun.bar.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.arun.bar.utils.FileUtils;
import com.arun.bar.utils.ToastUtils;

import java.io.File;

/**
 * Created by wy on 2017/5/5.
 */

public class SaveImageHelper {

    private static Bitmap createBitmap(View v) {
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }

    public static void saveToDisk(View view) {
        Bitmap bitmap = createBitmap(view);
        String fileName = System.currentTimeMillis() + ".jpg";
        if (!fileName.contains(".")) {
            fileName += ".jpg";
        } else if (fileName.contains(".") && fileName.contains("?")) {
            String[] strings = fileName.split("\\?", 2);
            fileName = strings[0];
        }
        boolean writtenToDisk = FileUtils.wirteBitmapToDisk(bitmap, fileName);
        String showData = "";
        if (writtenToDisk) {
            showData = "二维码海报已保存至" + FileUtils.DIR_IMAGE_SAVE + File.separator + fileName;
        } else {
            showData = "二维码海报保存失败，请开启sd卡存储权限";
        }
        ToastUtils.getInstance(view.getContext()).showToast(showData);
    }
}
