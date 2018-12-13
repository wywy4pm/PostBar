package com.arun.bar.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by wy on 2017/6/26.
 */

public class KeyBoardUtils {

    /**
     * 关闭软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);//强制隐藏键盘
    }
}
