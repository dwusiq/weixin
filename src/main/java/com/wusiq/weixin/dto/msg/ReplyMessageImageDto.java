package com.wusiq.weixin.dto.msg;

/**
 * 回复消息实体类-图片
 */
public class ReplyMessageImageDto extends BaseDto{
  private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}


class Image{
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}