package com.wusiq.weixin.service;

import com.wusiq.weixin.dto.req.Menu;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wicker on 2017/4/11.
 */
public interface WeiXinService {
    /**获取token*/
    String getAccessTken();
    /**获取微信服务器IP地址*/
    String getServiceIp();
    /**创建菜单*/
    boolean createMenu(Menu menu);
    /**处理微信的消息请求*/
    String processRequest(HttpServletRequest request);
}
