package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.PostModel;
import com.arun.bar.view.CommonView3;

import java.util.List;

public class PostPresenter extends BasePresenter<CommonView3> {
    public static final int TYPE_POST_ADD = 1;

    public PostPresenter() {
        super();
    }

    public void postAdd(String uuid, String post_title, String main_content, List<String> imgList) {
        PostModel.getInstance().postAdd(
                uuid, post_title, main_content, imgList, new RequestListenerImpl(getMvpView(), this) {
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (data != null && data.status == ErrorCode.SUCCESS) {
                            if (getMvpView() != null) {
                                getMvpView().refresh(TYPE_POST_ADD, data);
                            }
                        }
                    }
                });
    }
}
