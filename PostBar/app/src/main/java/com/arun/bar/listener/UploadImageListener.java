package com.arun.bar.listener;

/**
 * Created by WY on 2017/6/11.
 */
public interface UploadImageListener {
    void uploadPrepare(String imageUrl);

    void uploadSuccess(String imageUrl);
}
