package com.wusiq.weixin.dto.msg;

/**
 * 回复消息实体类-音乐
 */
public class ReplyMessageMusicDto extends BaseDto{
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}

class Music{
    private String Title;
    private String Description;
    private String MusicUrl;
    private String HQMusicUrl;
    private String ThumbMediaId;
}