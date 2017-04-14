package com.wusiq.weixin.dto.msg;

/**
 * 接收消息实体类-视频、小视频
 */
public class ReceiveMessageVideoDto extends ReceiveMessageBaseDto {
    private String MediaId;
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
