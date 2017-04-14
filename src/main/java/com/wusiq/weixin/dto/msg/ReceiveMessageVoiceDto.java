package com.wusiq.weixin.dto.msg;

/**
 * 接收消息实体类-语音---可能要优化
 */
public class ReceiveMessageVoiceDto extends ReceiveMessageBaseDto {
    private String MediaId;
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
