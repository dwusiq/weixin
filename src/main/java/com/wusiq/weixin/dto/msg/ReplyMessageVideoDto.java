package com.wusiq.weixin.dto.msg;

/**
 * 回复消息实体类-视频
 */
public class ReplyMessageVideoDto extends BaseDto{
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

class Video{
    private String MediaId;
    private String Title;
    private String Description;
}