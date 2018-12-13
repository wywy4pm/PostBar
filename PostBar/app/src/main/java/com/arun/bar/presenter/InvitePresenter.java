package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.UserModel;
import com.arun.bar.view.CommonView2;

public class InvitePresenter extends BasePresenter<CommonView2> {
    public InvitePresenter() {
        super();
    }

    public void joinBar(String user_uuid, String inviteCode) {
        UserModel.getInstance().joinBar(
                user_uuid, inviteCode, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            getMvpView().refresh(data.data);
                        }
                    }
                });
    }
}
