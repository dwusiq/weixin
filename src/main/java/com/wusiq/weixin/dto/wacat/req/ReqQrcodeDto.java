package com.wusiq.weixin.dto.wacat.req;

/**
 * 创建二维码ticket,请求微信服务出参
 */
public class ReqQrcodeDto {
    private String action_name;
    private ActionInfoDto action_info;

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfoDto getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfoDto action_info) {
        this.action_info = action_info;
    }
}
