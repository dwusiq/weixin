package com.wusiq.weixin.service;

import java.util.Map;

/**
 * 微信的事件推送处理服务
 */
public interface EventService {
  String eventClickDo(Map<String,String> map);
}
