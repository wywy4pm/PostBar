package com.arun.bar.bean;

/**
 * Created by wy on 2017/5/22.
 */

public class CommonApiResponse<T> {
    public String code;
    public int status;
    public T data;
    public String msg;
}
