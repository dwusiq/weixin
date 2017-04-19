package com.wusiq.weixin.service.impl;

import com.wusiq.weixin.base.Constant;
import com.wusiq.weixin.dto.req.Menu;
import com.wusiq.weixin.service.EventService;
import com.wusiq.weixin.service.MessageService;
import com.wusiq.weixin.service.WeiXinService;
import com.wusiq.weixin.utils.HttpUtils;
import com.wusiq.weixin.utils.MessageUtils;
import com.wusiq.weixin.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by wicker on 2017/4/11.
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
    private static Logger log = LoggerFactory.getLogger(WeiXinServiceImpl.class);
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";
    @Autowired
    MessageService MessageServiceImpl;
    @Autowired
    EventService EventServiceImpl;

    private static final String ACCESS_TOKEN = "accessToken";//redis中保存的access_token的键


    /***
     * 获取access_token
     */
    @Override
    public String getAccessTken() {
        //获取jedis
        Jedis jedis = RedisUtils.getJedis();
        //获取access_token
        String accessToken =jedis.get(ACCESS_TOKEN);
        if(StringUtils.isNotEmpty(accessToken)){
            RedisUtils.returnResource(jedis);
            return accessToken;
        }

        //请求地址和参数处理
        String url = Constant.WEIXIN_GETACCESS_TOKEN_URL;
        String reqUrl = String.format(url, Constant.WEIXIN_APPID,Constant.WEIXIN_SECRET);

        JSONObject jsonObject = HttpUtils.httpsRequest(REQUEST_METHOD_GET,"",reqUrl);

        jedis.setex(ACCESS_TOKEN,new Integer(jsonObject.getString("expires_in")),jsonObject.getString("access_token"));
        accessToken = jedis.get(ACCESS_TOKEN);
        //释放资源
        RedisUtils.returnResource(jedis);
        log.info("返回accessToken:{}",accessToken);

        return accessToken;
    }

    /**
     * 获取服务器IP
     */
    @Override
    public String getServiceIp() {
        String token = getAccessTken();
        String url = Constant.WEIXIN_GETCSERVICEIP_URL;
        String reqUrl = String.format(url,token);
        JSONObject jsonObject = HttpUtils.httpsRequest(REQUEST_METHOD_GET,"",reqUrl);
        String ip_list = jsonObject.getString("ip_list");
      //  String[] ipArr = ip_list.split(",");
        System.out.println(ip_list);
        return ip_list;
    }

    /**
     * 创建菜单
     */
    @Override
    public boolean createMenu(Menu menu) {
        String token = getAccessTken();
        String url = Constant.WEIXIN_CREATEMENU_URL;
        String reqUrl = String.format(url,token);
        JSONObject outObj = JSONObject.fromObject(menu);
        String outputStr = outObj.toString();
        JSONObject jsonObject = HttpUtils.httpsRequest(REQUEST_METHOD_POST,outputStr,reqUrl);
        if("0".equals(jsonObject.getString("errcode"))){
            return true;
        }
        return false;
    }

    /**
     * 根据微信请求的类别分别进行处理
     */
    @Override
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        Map<String,String> map = MessageUtils.readXML(request);
        String MsgType = map.get("MsgType");
        log.info("MsgType value:{}",map.get("MsgType"));

        if(Constant.REQ_MESSAGE_TYPE_TEXT.equals(MsgType)){
            //文本类型的消息请求
            respMessage = MessageServiceImpl.messageTextDo(map);
        }if(Constant.REQ_MESSAGE_TYPE_LOCATION.equals(MsgType)){
            //地理位置消息请求
            respMessage = MessageServiceImpl.messageLocationDo(map);
        }if(Constant.REQ_MESSAGE_TYPE_EVENT.equals(MsgType)){
            String event = map.get("Event");
            log.info("Event value:{}",event);
            //事件请求
            if(Constant.REQ_EVENT_TYPE_CLICK.equals(event)){
                //点击事件类型
                respMessage = EventServiceImpl.eventClickDo(map);
            }

        }
        return respMessage;
    }

    /**新增临时媒体素材*/
    @Override
    public JSONObject uploadMedia(String filePath,String mediaType) {
        String accessToken = getAccessTken();
        String url = Constant.WEIXIN_MEDIA_UPLOAD_URL;
        url = String.format(url,accessToken,mediaType);
        JSONObject jsonObject = HttpUtils.uploadFile(url,filePath);
        return jsonObject;
    }

    /**
     * 下载微信媒体素材（不包含视频）
     */
    @Override
    public String downloadMedia(String mediaId, String fileSavePath) {
        log.info("downloadVideo param:[mediaId:{},fileSavePath:{}]",mediaId,fileSavePath);
        //非空判断
        if(StringUtils.isEmpty(mediaId)){
            log.warn("downloadVideo fail:mediaId is empty");return null;
        }
        if(StringUtils.isEmpty(fileSavePath)){
            log.warn("downloadVideo fail:fileSavePath is empty");return null;
        }

        //媒体素材
        String accessToken = getAccessTken();
        String requestUrl = String.format(Constant.WEIXIN_MEDIA_GET_URL,accessToken,mediaId);
        log.info("requestUrl:{}",requestUrl);
        //下载视频
        String savePath = HttpUtils.downloadFile(requestUrl,fileSavePath);

        log.info("downloadVideo return:[savePath:{}]",savePath);
        return savePath;
    }

    /**
     * 下载微信媒体素材（视频）
     */
    @Override
    public String downloadVideo(String mediaId, String fileSavePath) {
        log.info("downloadVideo param:[mediaId:{},fileSavePath:{}]",mediaId,fileSavePath);
        //非空判断
        if(StringUtils.isEmpty(mediaId)){
            log.warn("downloadVideo fail:mediaId is empty");return null;
        }
        if(StringUtils.isEmpty(fileSavePath)){
            log.warn("downloadVideo fail:fileSavePath is empty");return null;
        }

        //获取视频地址
        String accessToken = getAccessTken();
        String requestUrl = String.format(Constant.WEIXIN_MEDIA_GET_VIDEO_URL,accessToken,mediaId);
        JSONObject jsonObject = HttpUtils.httpRequest(REQUEST_METHOD_GET,requestUrl);
        String video_url = jsonObject.getString("video_url");
        log.info("video_url:{}",video_url);

        //下载视频
        String savePath = HttpUtils.downloadFile(video_url,fileSavePath);

        log.info("downloadVideo return:[savePath:{}]",savePath);
        return savePath;
    }
}
