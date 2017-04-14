package com.wusiq.weixin.dto.msg;

/**
 * 接收事件实体类-扫描带参数二维码(用户已经关注和未关注)
 */
public class ReceiveEventSubscribeDto extends ReceiveEventBaseDto {
    private String EventKey;
    private String Ticket;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
