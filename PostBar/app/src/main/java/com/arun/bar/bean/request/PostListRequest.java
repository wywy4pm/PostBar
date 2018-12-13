package com.arun.bar.bean.request;

public class PostListRequest {
    private int pageSize;
    private int currentPage;
    private boolean pagination;
    private ParamObj paramObj;

    public PostListRequest(String bar_uuid, int currentPage, int pageSize, boolean pagination) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pagination = pagination;
        this.paramObj = new ParamObj(bar_uuid);
    }

    private static class ParamObj {
        private String bar_uuid;

        private ParamObj(String bar_uuid) {
            this.bar_uuid = bar_uuid;
        }
    }
}
