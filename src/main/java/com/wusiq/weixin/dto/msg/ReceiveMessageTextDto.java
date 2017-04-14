package com.wusiq.weixin.dto.msg;

/**
 * 接收消息实体类-文本
 */
public class ReceiveMessageTextDto extends ReceiveMessageBaseDto {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
