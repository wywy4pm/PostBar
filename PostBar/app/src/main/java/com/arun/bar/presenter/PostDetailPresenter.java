package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.bean.PostListData;
import com.arun.bar.bean.ReplyListData;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.PostModel;
import com.arun.bar.view.CommonView4;

public class PostDetailPresenter extends BasePresenter<CommonView4> {
    public static final int TYPE_REPLY_ADD = 1;

    public PostDetailPresenter() {
        super();
    }

    public void getReplyList(String post_uuid, int currentPage, int pageSize, final boolean pagination) {
        PostModel.getInstance().getReplyList(
                post_uuid, currentPage, pageSize, pagination, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            if (data.data != null && data.data instanceof ReplyListData) {
                                if (!pagination) {
                                    getMvpView().refresh(data.data);
                                } else {
                                    getMvpView().refreshMore(data.data);
                                }
                            }
                        }
                    }
                });
    }

    public void replyAdd(String reply_content, String post_uuid, String user_uuid) {
        PostModel.getInstance().replyAdd(
                reply_content, post_uuid, user_uuid, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            getMvpView().refresh(TYPE_REPLY_ADD, data.data);
                        }
                    }
                });
    }
}
