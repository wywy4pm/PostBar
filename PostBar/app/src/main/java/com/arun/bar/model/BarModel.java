package com.arun.bar.model;

import com.arun.bar.bean.request.BarAddRequest;
import com.arun.bar.listener.CommonRequestListener;
import com.arun.bar.retrofit.RetrofitInit;

public class BarModel extends BaseModel {
    private volatile static BarModel instance;

    public static BarModel getInstance() {
        if (instance == null) {
            synchronized (BarModel.class) {
                if (instance == null) {
                    instance = new BarModel();
                }
            }
        }
        return instance;
    }

    public void barAdd(String barName, String uuid, CommonRequestListener listener) {
        BarAddRequest barAddRequest = new BarAddRequest(barName, uuid);
        request(RetrofitInit.getApi().barAdd(barAddRequest), listener);
    }
}
