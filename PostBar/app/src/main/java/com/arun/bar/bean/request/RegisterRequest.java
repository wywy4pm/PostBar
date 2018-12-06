package com.arun.bar.bean.request;

public class RegisterRequest {
    private String phone_num;
    private String uuid;
    private String user_name;
    private String nick_name;

    public RegisterRequest(String phoneNum) {
        this.phone_num = phoneNum;
    }

    public RegisterRequest(String phoneNum, String uuid, String userName, String nickName) {
        this.phone_num = phoneNum;
        this.uuid = uuid;
        this.user_name = userName;
        this.nick_name = nickName;
    }
}
