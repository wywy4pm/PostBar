package com.arun.bar.bean.request;

public class UserUpdateRequest {
    public String email;
    public String id_type;
    public String openid;
    public String password;
    public String phone_num;
    public String photo_addr;
    public String user_name;
    public String uuid;
    public String nick_name;

    public UserUpdateRequest(String uuid,String nick_name) {
        this.uuid = uuid;
        this.nick_name = nick_name;
    }
}
