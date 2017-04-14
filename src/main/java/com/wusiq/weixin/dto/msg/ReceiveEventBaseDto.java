package com.wusiq.weixin.dto.msg;

/**
 * 接收事件实体类-基类
 */
public class ReceiveEventBaseDto extends BaseDto {
    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}
