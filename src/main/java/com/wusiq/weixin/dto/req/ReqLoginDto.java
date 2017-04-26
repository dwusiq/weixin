package com.wusiq.weixin.dto.req;

/**
 * 登陆-页面请求入参实体类
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
