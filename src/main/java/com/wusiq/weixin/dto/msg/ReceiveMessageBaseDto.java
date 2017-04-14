package com.wusiq.weixin.dto.msg;

/**
 * 接收消息实体类-基类
 */
public class ReceiveMessageBaseDto extends BaseDto {
    private String MsgId;

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
