package com.wusiq.weixin.dto.msg;

/**
 * 接收事件实体类-自定义菜单事件
 */
public class ReceiveEventClickDto extends ReceiveEventBaseDto{
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
