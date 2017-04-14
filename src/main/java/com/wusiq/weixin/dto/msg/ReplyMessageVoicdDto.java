package com.wusiq.weixin.dto.msg;

/**
 * 回复消息实体类-语音
 */
public class ReplyMessageVoicdDto extends BaseDto{
   private Voice voice;

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}


class Voice{
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}