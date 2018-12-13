package com.arun.bar.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.arun.bar.MMApplication;
import com.arun.bar.listener.UploadImageListener;
import com.arun.bar.utils.FileUtils;
import com.arun.bar.utils.SharedPreferencesUtils;

/**
 * Created by wy on 2017/6/10.
 */

public class OssUploadImageHelper {

    public static void uploadImage(Context context, Uri uri, final UploadImageListener uploadImageListener) {
        String uploadFilePath = FileUtils.getRealFilePathByUri(context, uri);
        String fileType = FileUtils.getFileTypeByPath(uploadFilePath);
        final String objectKey = MMApplication.OSS_UPLOAD_IMAGE_FOLDER + SharedPreferencesUtils.getUid(context) + "_" + System.currentTimeMillis() + "." + fileType;

        String imageUrl = MMApplication.IMAGE_URL_BASE + objectKey;
        if (uploadImageListener != null) {
            uploadImageListener.uploadPrepare(imageUrl);
        }

        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(MMApplication.OSS_BUCKET_NAME, objectKey, uploadFilePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                /*int progress = (int) ((double) currentSize / totalSize * 100);
                Log.d("onProgress", "progress = " + progress + "%");*/
            }
        });
        OSSAsyncTask task = MMApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                String imageUrl = MMApplication.IMAGE_URL_BASE + objectKey;

                if (uploadImageListener != null) {
                    uploadImageListener.uploadSuccess(imageUrl);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public static void uploadImage(final String uploadFilePath, final String objectKey, final UploadImageListener uploadImageListener) {

        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(MMApplication.OSS_BUCKET_NAME, objectKey, uploadFilePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                /*int progress = (int) ((double) currentSize / totalSize * 100);
                Log.d("onProgress", "progress = " + progress + "%");*/
            }
        });
        OSSAsyncTask task = MMApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                /*Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());*/

                String imageUrl = MMApplication.IMAGE_URL_BASE + objectKey;

                if (uploadImageListener != null) {
                    uploadImageListener.uploadSuccess(imageUrl);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

}
