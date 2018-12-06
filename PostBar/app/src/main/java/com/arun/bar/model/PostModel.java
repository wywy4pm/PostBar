package com.arun.bar.model;

import com.arun.bar.bean.request.PostListRequest;
import com.arun.bar.listener.CommonRequestListener;
import com.arun.bar.retrofit.RetrofitInit;

public class PostModel extends BaseModel {
    private volatile static PostModel instance;

    public static PostModel getInstance() {
        if (instance == null) {
            synchronized (PostModel.class) {
                if (instance == null) {
                    instance = new PostModel();
                }
            }
        }
        return instance;
    }

    public void getPostList(String uuid, int currentPage, int pageSize, boolean pagination, CommonRequestListener listener) {
        PostListRequest postListRequest = new PostListRequest(uuid, currentPage, pageSize, pagination);
        request(RetrofitInit.getApi().getPostList(postListRequest), listener);
    }
}
