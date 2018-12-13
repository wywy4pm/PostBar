package com.arun.bar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arun.bar.R;
import com.arun.bar.bean.UploadImageBean;
import com.arun.bar.listener.ImagePickerListener;
import com.arun.bar.utils.DensityUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wy on 2017/6/8.
 */

public class UploadImageAdapter extends BaseListAdapter<UploadImageBean> {
    private ImagePickerListener imagePickerListener;

    public UploadImageAdapter(Context context, List<UploadImageBean> list) {
        super(context, list);
    }

    public void setImagePickerListener(ImagePickerListener imagePickerListener) {
        this.imagePickerListener = imagePickerListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UploadImageHolder uploadImageHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_upload_image, parent, false);
            uploadImageHolder = new UploadImageHolder(context, convertView);
            convertView.setTag(uploadImageHolder);
        } else {
            uploadImageHolder = (UploadImageHolder) convertView.getTag();
        }
        UploadImageBean bean = getItem(position);
        if (bean.isUpload == 1) {
            uploadImageHolder.photo_close.setVisibility(View.VISIBLE);
            uploadImageHolder.layout_add_photo.setVisibility(View.GONE);
            uploadImageHolder.image_picture.setVisibility(View.VISIBLE);

            Glide.with(context).load(bean.imageUri).centerCrop().into(uploadImageHolder.image_picture);
            uploadImageHolder.photo_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagePickerListener != null) {
                        imagePickerListener.removeSelect(position);
                    }
                }
            });

            convertView.setClickable(false);
        } else {
            uploadImageHolder.photo_close.setVisibility(View.GONE);
            uploadImageHolder.layout_add_photo.setVisibility(View.VISIBLE);
            uploadImageHolder.image_picture.setVisibility(View.GONE);

            convertView.setClickable(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagePickerListener != null) {
                        imagePickerListener.openImagePicker();
                    }
                }
            });
        }
        return convertView;
    }


    private static class UploadImageHolder {
        private ImageView photo_close;
        private ImageView add_photo;
        private ImageView image_picture;
        private RelativeLayout layout_add_photo;

        private UploadImageHolder(Context context, View rootView) {
            this.photo_close = (ImageView) rootView.findViewById(R.id.photo_close);
            this.add_photo = (ImageView) rootView.findViewById(R.id.add_photo);
            this.image_picture = (ImageView) rootView.findViewById(R.id.image_picture);
            this.layout_add_photo = (RelativeLayout) rootView.findViewById(R.id.layout_add_photo);
            if (rootView.getLayoutParams() != null) {
                int widthHeight = (DensityUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 24) - 2 * DensityUtils.dp2px(context, 2)) / 3;
                rootView.getLayoutParams().width = widthHeight;
                rootView.getLayoutParams().height = widthHeight;
            }
        }
    }
}
