package com.wusiq.weixin.service;

import java.util.Map;

/**
 * 组织返回消息给微信的服务
 */
public interface ReplyMessageService {
    //组织返回文本消息
    String replyMessageText(Map<String,String> map, String content);
}
