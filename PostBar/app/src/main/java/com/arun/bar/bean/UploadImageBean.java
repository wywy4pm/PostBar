package com.arun.bar.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wy on 2017/6/8.
 */

public class UploadImageBean implements Parcelable {
    public int isUpload;//0.不是上传图片 1.是上传图片
    public Uri imageUri;
    public String imageUrl;
    //public String key;

    public UploadImageBean(int isUpload, Uri imageUri) {
        this.isUpload = isUpload;
        this.imageUri = imageUri;
    }

    /*public UploadImageBean(int isUpload, Uri imageUri, String key) {
        this.isUpload = isUpload;
        this.imageUri = imageUri;
        this.key = key;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isUpload);
        dest.writeParcelable(imageUri, flags);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<UploadImageBean> CREATOR = new Creator<UploadImageBean>() {
        @Override
        public UploadImageBean[] newArray(int size) {
            return new UploadImageBean[size];
        }

        @Override
        public UploadImageBean createFromParcel(Parcel in) {
            return new UploadImageBean(in);
        }
    };

    public UploadImageBean(Parcel in) {
        isUpload = in.readInt();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        imageUrl = in.readString();
    }

}
