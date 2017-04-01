package com.wusiq.weixin.dto.req;

/**
 * Created by wicker on 2017/3/31.
 */
public class ReqLoginDto {
    private String username;
    private String userpwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }
}
