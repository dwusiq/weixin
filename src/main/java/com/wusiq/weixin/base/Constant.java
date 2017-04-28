package com.wusiq.weixin.base;

/**
 * Created by wicker on 2017/4/7.
 */
public class Constant {
    /**
     * 一般常量
     */
    /**微信APPID*/
    public static final String WEIXIN_APPID="wx3014ef78c720efc0";
    /**微信APPID*/
    public static final String WEIXIN_SECRET="0e7d8f54abfee4ce832bcfed26e14d65";

    /**
     * 请求地址
     */
    /**获取access_token的地址*/
    public static final String WEIXIN_GETACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%1s&secret=%2s";
    /**获取微信服务器IP地址*/
    public static final String WEIXIN_GETCSERVICEIP_URL="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%1s";
    /**创建菜单*/
    public static final String WEIXIN_CREATEMENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%1s";
    /**新增临时素材*/
    public static final String WEIXIN_MEDIA_UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%1s&type=%2s";
    /**获取临时素材*/
    public static final String WEIXIN_MEDIA_GET_URL="https://api.weixin.qq.com/cgi-bin/media/get?access_token=%1s&media_id=%2s";
    /**获取临时素材-视频*/
    public static final String WEIXIN_MEDIA_GET_VIDEO_URL="http://api.weixin.qq.com/cgi-bin/media/get?access_token=%1s&media_id=%2s";
    /**创建临时二维码ticket*/
    public static final String WEIXIN_CREATE_TEMP_QRCODE_TICKET_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%1s";




    /**
     * 请求消息的类型
     * */
    /**文本消息*/
    public static final String REQ_MESSAGE_TYPE_TEXT="text";
    /**图片消息*/
    public static final String REQ_MESSAGE_TYPE_IMAGE="image";
    /**语音消息*/
    public static final String REQ_MESSAGE_TYPE_VOICE="voice";
    /**视频消息*/
    public static final String REQ_MESSAGE_TYPE_VIDEO="video";
    /**地理位置消息*/
    public static final String REQ_MESSAGE_TYPE_LOCATION="location";
    /**链接消息*/
    public static final String REQ_MESSAGE_TYPE_LINK="link";
    /**事件*/
    public static final String REQ_MESSAGE_TYPE_EVENT="event";

    /**
     * 请求事件的类型
     * */
    /**扫描带参数二维码事件*/
    public static final String REQ_EVENT_TYPE_SUBSCRIBE="subscribe";
    /**上报地理位置事件*/
    public static final String REQ_EVENT_TYPE_LOCATION="LOCATION";
    /**自定义菜单事件*/
    public static final String REQ_EVENT_TYPE_CLICK="CLICK";
    /**点击菜单跳转链接时的事件*/
    public static final String REQ_EVENT_TYPE_VIEW="VIEW";
}
