package com.arun.bar.bean.request;

import java.util.List;

public class PostAddRequest {
    public String bar_uuid;
    public String main_content;
    public int post_level;
    public String post_owner;
    public String post_time;
    public String post_title;
    //public String uuid;
    public List<String> imgList;

    public PostAddRequest(String post_owner, String post_title, String main_content) {
        this.post_owner = post_owner;
        this.post_title = post_title;
        this.main_content = main_content;
    }
}
