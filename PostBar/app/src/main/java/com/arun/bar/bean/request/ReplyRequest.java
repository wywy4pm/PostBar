package com.arun.bar.bean.request;

public class ReplyRequest {
    private String bar_uuid;
    private String main_content;
    private String post_uuid;
    private String user_uuid;

    public ReplyRequest(String main_content, String post_uuid, String user_uuid) {
        this.main_content = main_content;
        this.post_uuid = post_uuid;
        this.user_uuid = user_uuid;
    }
}
