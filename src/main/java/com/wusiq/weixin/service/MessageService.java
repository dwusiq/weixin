package com.wusiq.weixin.service;

import java.util.Map;

/**
 * 微信的消息请求处理服务
 */
public interface MessageService {
    /**处理微信的文本消息请求*/
    String messageTextDo(Map<String,String> map);
    /**处理微信的地理位置消息请求*/
    String messageLocationDo(Map<String,String> map);
}
