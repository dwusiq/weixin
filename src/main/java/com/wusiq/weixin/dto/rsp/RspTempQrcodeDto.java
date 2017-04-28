package com.wusiq.weixin.dto.rsp;

/**
 * 创建临时二维码ticket,返回页面的参数
 */
public class RspTempQrcodeDto {
    private String ticket;//	获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
    private Integer expire_seconds;//	该二维码有效时间，以秒为单位。 最大不超过604800（即7天）。
    private String url;//	二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(Integer expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
