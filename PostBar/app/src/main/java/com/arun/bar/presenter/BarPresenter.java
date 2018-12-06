package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.BarModel;
import com.arun.bar.view.CommonView2;

public class BarPresenter extends BasePresenter<CommonView2> {
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
                            getMvpView().refresh(data.data);
                        }
                    }
                });
    }
}
