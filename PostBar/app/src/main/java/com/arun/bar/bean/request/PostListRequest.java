package com.arun.bar.bean.request;

public class PostListRequest {
    private int pageSize;
    private int currentPage;
    private boolean pagination;
    private ParamObj paramObj;

    public PostListRequest(String uuid, int currentPage, int pageSize, boolean pagination) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pagination = pagination;
        this.paramObj = new ParamObj(uuid);
    }

    public static class ParamObj {
        public String uuid;

        private ParamObj(String uuid) {
            this.uuid = uuid;
        }
    }
}
