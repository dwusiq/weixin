package com.wusiq.weixin.service.impl;

import com.wusiq.weixin.service.EventService;
import com.wusiq.weixin.service.ReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信的事件推送处理服务
 */
@Service
public class EventServiceImpl implements EventService{
    @Autowired
    ReplyMessageService ReplyMessageServiceImpl;
    /**
     *微信点击菜单拉取消息时的事件推送
     */
    @Override
    public String eventClickDo(Map<String, String> map) {
        String rspStr = "暂未能提供该服务，敬请等待！";
        if ("11".equals(map.get("EventKey"))) {
            rspStr = "您选择的交通工具是公交";
        }else if ("12".equals(map.get("EventKey"))) {
            rspStr = "您选择的交通工具是地铁";
        }else if ("13".equals(map.get("EventKey"))) {
            rspStr = "您选择的交通工具是自行车";
        }

        //返回的消息
        String respMessage = ReplyMessageServiceImpl.replyMessageText(map,rspStr);
        return respMessage;
    }
}
