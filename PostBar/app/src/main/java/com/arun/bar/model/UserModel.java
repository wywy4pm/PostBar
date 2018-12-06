package com.arun.bar.model;

import com.arun.bar.bean.request.RegisterRequest;
import com.arun.bar.listener.CommonRequestListener;
import com.arun.bar.retrofit.RetrofitInit;

import io.reactivex.Observer;

public class UserModel extends BaseModel {
    private volatile static UserModel instance;

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    public void register(String phoneNum, CommonRequestListener listener) {
        RegisterRequest registerRequest = new RegisterRequest(phoneNum);
        request(RetrofitInit.getApi().register(registerRequest), listener);
    }
}