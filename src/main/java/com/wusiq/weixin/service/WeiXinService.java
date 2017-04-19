package com.wusiq.weixin.service;

import com.wusiq.weixin.dto.req.Menu;
import net.sf.json.JSONObject;

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
    /**新增临时素材*/
    JSONObject uploadMedia(String filePath, String mediaType);
    /**获取临时素材*/
    String downloadMedia(String mediaId,String fileSavePath);
    /**获取视频素材*/
    String downloadVideo(String mediaId,String fileSavePath);

}
