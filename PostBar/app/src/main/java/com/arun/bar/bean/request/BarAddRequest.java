package com.arun.bar.bean.request;

public class BarAddRequest {
    private String bar_name;
    private String uuid;

    public BarAddRequest(String bar_name, String uuid) {
        this.bar_name = bar_name;
        this.uuid = uuid;
    }
}
