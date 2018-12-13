package com.arun.bar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arun.bar.adapter.UploadImageAdapter;
import com.arun.bar.bean.UploadImageBean;
import com.arun.bar.common.Constant;
import com.arun.bar.event.UpdateMainEvent;
import com.arun.bar.helper.MatisseHelper;
import com.arun.bar.helper.OssUploadImageHelper;
import com.arun.bar.listener.ImagePickerListener;
import com.arun.bar.listener.UploadImageListener;
import com.arun.bar.matisse.ui.MatisseActivity;
import com.arun.bar.presenter.PostPresenter;
import com.arun.bar.utils.DensityUtils;
import com.arun.bar.utils.FileUtils;
import com.arun.bar.utils.PermissionUtils;
import com.arun.bar.utils.UploadImageUtils;
import com.arun.bar.view.CommonView3;
import com.arun.bar.widget.GridViewForScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends BaseActivity implements ImagePickerListener, CommonView3 {
    private EditText post_title;
    private EditText post_description;
    private GridViewForScrollView gridView;
    private TextView image_right;
    private List<UploadImageBean> images = new ArrayList<>();
    private UploadImageAdapter uploadImageAdapter;
    //private static final int REQUEST_CODE_CHOOSE = 1;
    private List<Uri> mSelected;
    //存放需要上传服务器的imageUrl
    //private List<MsgImgRequest> uploadImages = new ArrayList<>();
    private PostPresenter postPresenter;
    public static final String BACK_MODE_SEND_POST = "send_post";
    private String barName;

    public static void jumpToSendPost(Context context, String barName) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(Constant.INTENT_BAR_NAME, barName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
        initData();
    }

    private void initView() {
        post_title = findViewById(R.id.post_title);
        post_description = findViewById(R.id.post_description);
        gridView = findViewById(R.id.gridView);
        uploadImageAdapter = new UploadImageAdapter(this, images);
        uploadImageAdapter.setImagePickerListener(this);
        gridView.setAdapter(uploadImageAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            barName = getIntent().getExtras().getString(Constant.INTENT_BAR_NAME);
        }

        setTitleName("发布到" + barName);
        setRight();
        setBack();
    }

    private void setRight() {
        image_right = findViewById(R.id.image_right);
        if (image_right.getLayoutParams() != null
                && image_right.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            image_right.getLayoutParams().height = DensityUtils.dp2px(this, 22);
            image_right.getLayoutParams().width = DensityUtils.dp2px(this, 46);
            ((ConstraintLayout.LayoutParams) image_right.getLayoutParams())
                    .setMargins(DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
        }
        image_right.setVisibility(View.VISIBLE);
        image_right.setText("发布");
        image_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        image_right.setBackgroundResource(R.drawable.shape_btn_reply);
        image_right.setTextColor(getResources().getColor(R.color.white));
        image_right.setGravity(Gravity.CENTER);
        image_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        images.add(new UploadImageBean(0, null));
        uploadImageAdapter.notifyDataSetChanged();
        if (postPresenter == null) {
            postPresenter = new PostPresenter();
            postPresenter.attachView(this);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (getIntent() != null) {
            mSelected = getIntent().getParcelableArrayListExtra(MatisseActivity.EXTRA_RESULT_SELECTION);

            if (mSelected != null && mSelected.size() > 0) {
                if (images != null && images.size() > 0) {
                    images.remove(images.size() - 1);
                }
                for (int i = 0; i < mSelected.size(); i++) {
                    if (images.size() < 9) {

                        String realFilePath = FileUtils.getRealFilePathByUri(this, mSelected.get(i));
                        String fileType = FileUtils.getFileTypeByPath(realFilePath);
                        String objectKey = MMApplication.OSS_UPLOAD_IMAGE_FOLDER + getUserId() + "_" + System.currentTimeMillis() + "." + fileType;

                        UploadImageBean bean = new UploadImageBean(1, mSelected.get(i));
                        bean.imageUrl = MMApplication.IMAGE_URL_BASE + objectKey;
                        images.add(bean);

                        OssUploadImageHelper.uploadImage(realFilePath, objectKey,
                                new UploadImageListener() {
                                    @Override
                                    public void uploadPrepare(String imageUrl) {
                                    }

                                    @Override
                                    public void uploadSuccess(String imageUrl) {
                                        Log.d("TAG", "imageUrl = " + imageUrl);
                                    }
                                });
                    }
                }
                if (images.size() < 9) {
                    images.add(new UploadImageBean(0, null));
                }
                uploadImageAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void openImagePicker() {
        if (FileUtils.hasSdcard() && PermissionUtils.hasPermission(this, PermissionUtils.WRITE_EXTERNAL_STORAGE)) {
            MatisseHelper.startPicturePicker(this, BACK_MODE_SEND_POST);
        } else {
            showToast("请开启sd卡存储权限");
        }
    }

    @Override
    public void removeSelect(int position) {
        if (images != null && images.size() > 0 && position <= images.size() - 1) {
            images.remove(position);
            if (UploadImageUtils.getSelectCount(images) == 8) {
                images.add(new UploadImageBean(0, null));
            }
            uploadImageAdapter.notifyDataSetChanged();
        }
    }

    public void sendPost() {
        if (postPresenter != null) {
            if (TextUtils.isEmpty(post_title.getText())) {
                showToast("请输入标题");
                return;
            } else if (TextUtils.isEmpty(post_description.getText()) && (images.size() < 2)) {
                showToast("请填写帖子内容或图片");
                return;
            }
            postPresenter.postAdd(getUserId(), post_title.getText().toString(), post_description.getText().toString(), UploadImageUtils.getUploadImages(images));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postPresenter != null) {
            postPresenter.detachView();
        }
    }

    @Override
    public void refresh(int type, Object data) {
        if (type == PostPresenter.TYPE_POST_ADD) {
            showToast("发布成功");
            finish();
            EventBus.getDefault().post(new UpdateMainEvent());
        }
    }
}
