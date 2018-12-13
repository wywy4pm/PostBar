package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.bean.PostListData;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.PostModel;
import com.arun.bar.view.CommonView;

public class MainPresenter extends BasePresenter<CommonView> {
    public MainPresenter() {
        super();
    }

    public void getPostList(String bar_uuid, int currentPage, int pageSize, final boolean pagination) {
        PostModel.getInstance().getPostList(
                bar_uuid, currentPage, pageSize, pagination, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            if (data.data != null && data.data instanceof PostListData) {
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
}
