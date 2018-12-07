package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.BarModel;
import com.arun.bar.model.UserModel;
import com.arun.bar.view.CommonView3;

public class BarPresenter extends BasePresenter<CommonView3> {
    public static final int TYPE_BAR_ADD = 1;
    public static final int TYPE_USER_UPDATE = 2;

    public BarPresenter() {
        super();
    }

    public void barAdd(String barName, String uuid) {
        BarModel.getInstance()
                .barAdd(barName, uuid, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            getMvpView().refresh(TYPE_BAR_ADD, data.data);
                        }
                    }
                });
    }

    public void userUpdate(String uuid, String nickName) {
        UserModel.getInstance().updateUser(
                uuid, nickName, new RequestListenerImpl(getMvpView(), this) {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onSuccess(CommonApiResponse data) {
                        if (getMvpView() != null && data != null && data.status == ErrorCode.SUCCESS) {
                            getMvpView().refresh(TYPE_USER_UPDATE, data.data);
                        }
                    }
                });
    }
}
