package com.arun.bar.bean.request;

public class ReplyListRequest {

    private int currentPage;
    private int pageSize;
    private boolean pagination;
    private ParamObjBean paramObj;

    public ReplyListRequest(String post_uuid, int currentPage, int pageSize, boolean pagination) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pagination = pagination;
        this.paramObj = new ParamObjBean(post_uuid);
    }

    private static class ParamObjBean {
        private String post_uuid;

        private ParamObjBean(String post_uuid) {
            this.post_uuid = post_uuid;
        }
    }

}
