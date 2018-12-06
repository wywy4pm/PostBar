package com.arun.bar.presenter;

import com.arun.bar.bean.CommonApiResponse;
import com.arun.bar.common.ErrorCode;
import com.arun.bar.listener.RequestListenerImpl;
import com.arun.bar.model.UserModel;
import com.arun.bar.view.CommonView2;

public class RegisterPresenter extends BasePresenter<CommonView2>{
    public RegisterPresenter(){
        super();
    }
    public void register(String phoneNum){
        UserModel.getInstance()
                .register(phoneNum, new RequestListenerImpl(getMvpView(),this) {
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
