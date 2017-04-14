package com.wusiq.weixin.dto.msg;

/**
 * 回复消息实体类-文本
 */
public class ReplyMessageTextDto extends BaseDto{
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
