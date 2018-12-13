package com.arun.bar.model;

import com.arun.bar.bean.request.PostAddRequest;
import com.arun.bar.bean.request.PostListRequest;
import com.arun.bar.bean.request.ReplyListRequest;
import com.arun.bar.bean.request.ReplyRequest;
import com.arun.bar.listener.CommonRequestListener;
import com.arun.bar.retrofit.RetrofitInit;

import java.util.List;

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

    public void getPostList(String bar_uuid, int currentPage, int pageSize, boolean pagination, CommonRequestListener listener) {
        PostListRequest postListRequest = new PostListRequest(bar_uuid, currentPage, pageSize, pagination);
        request(RetrofitInit.getApi().getPostList(postListRequest), listener);
    }

    public void postAdd(String uuid, String post_title, String main_content, List<String> imgList, CommonRequestListener listener) {
        PostAddRequest postAddRequest = new PostAddRequest(uuid, post_title, main_content);
        request(RetrofitInit.getApi().postAdd(postAddRequest, imgList), listener);
    }

    public void getReplyList(String post_uuid, int currentPage, int pageSize, boolean pagination, CommonRequestListener listener) {
        ReplyListRequest replyListRequest = new ReplyListRequest(post_uuid, currentPage, pageSize, pagination);
        request(RetrofitInit.getApi().getReplyList(replyListRequest), listener);
    }

    public void replyAdd(String reply_content, String post_uuid, String user_uuid, CommonRequestListener listener) {
        ReplyRequest replyRequest = new ReplyRequest(reply_content, post_uuid, user_uuid);
        request(RetrofitInit.getApi().replyAdd(replyRequest), listener);
    }
}
