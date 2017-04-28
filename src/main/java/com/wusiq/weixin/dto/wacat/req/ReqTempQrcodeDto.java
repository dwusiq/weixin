package com.wusiq.weixin.dto.wacat.req;

/**
 * 创建临时二维码ticket,请求微信服务出参
 */
public class ReqTempQrcodeDto extends ReqQrcodeDto{
    private Integer expire_seconds;

    public Integer getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(Integer expire_seconds) {
        this.expire_seconds = expire_seconds;
    }
}
