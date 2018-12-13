package com.arun.bar.utils;

import com.arun.bar.bean.UploadImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/6/27.
 */

public class UploadImageUtils {

    /**
     * 获取选中图片的数量
     *
     * @param images
     * @return
     */
    public static int getSelectCount(List<UploadImageBean> images) {
        int count = 0;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).isUpload == 1) {
                count += 1;
            }
        }
        return count;
    }


    /**
     * 获取需要传给服务器的所有图片
     *
     * @param images
     * @return
     */
    public static List<String> getUploadImages(List<UploadImageBean> images) {
        List<String> uploadImages = new ArrayList<>();
        if (images.size() == 9) {
            for (int i = 0; i < images.size(); i++) {
                uploadImages.add(images.get(i).imageUrl);
            }
        } else {
            for (int i = 0; i < images.size() - 1; i++) {
                uploadImages.add(images.get(i).imageUrl);
            }
        }
        return uploadImages;
    }
}
