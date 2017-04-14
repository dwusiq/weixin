package com.wusiq.weixin.dto.msg;

/**
 * 接收消息实体类-图片
 */
public class ReceiveMessageImageDto extends ReceiveMessageBaseDto {
    private String PicUrl;
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
