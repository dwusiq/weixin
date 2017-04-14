package com.wusiq.weixin.service.impl;

import com.wusiq.weixin.base.Constant;
import com.wusiq.weixin.dto.req.Menu;
import com.wusiq.weixin.service.EventService;
import com.wusiq.weixin.service.MessageService;
import com.wusiq.weixin.service.WeiXinService;
import com.wusiq.weixin.utils.MessageUtils;
import com.wusiq.weixin.utils.MyX509TrustManager;
import com.wusiq.weixin.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wicker on 2017/4/11.
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
    private static Logger log = LoggerFactory.getLogger(WeiXinServiceImpl.class);
    @Autowired
    MessageService MessageServiceImpl;
    @Autowired
    EventService EventServiceImpl;
    private static final String ENCODING="UTF-8";//编码格式
    private static final String TIME_OUT = "30000";//超时时间为30秒
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

        JSONObject json = sendGet(reqUrl);

        jedis.setex(ACCESS_TOKEN,new Integer(json.getString("expires_in")),json.getString("access_token"));
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
        String urlReq = String.format(url,token);
        JSONObject obj = sendGet(urlReq);
        String ip_list = obj.getString("ip_list");
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
        String urlReq = String.format(url,token);
        JSONObject outObj = JSONObject.fromObject(menu);
        String outputStr = outObj.toString();
        JSONObject obj = httpRequest(urlReq,"POST",outputStr);
        if("0".equals(obj.getString("errcode"))){
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


    private static JSONObject sendGet(String url){
        log.info("request weChat url:{}",url);
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // get请求方式
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", TIME_OUT);// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", TIME_OUT); // 读取超时30秒
            http.connect();

            if(200==http.getResponseCode()){
                InputStream is = http.getInputStream();
                int size = is.available();
                log.info("is size:",size);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len = is.read(bytes))!=-1){
                    baos.write(bytes,0,len);
                    baos.flush();
                }
                String rspStr = baos.toString(ENCODING);
                log.info("weChat response：{}",rspStr);
                JSONObject obj = JSONObject.fromObject(rspStr);
                is.close();
                return obj;
            }
        }catch (Exception ex){
            log.warn("weixinRequest fail,{}",ex);
            return null;
        }
        return null;
    }


    /**
     * 描述:  发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        log.info("requestUrl value:{}",requestUrl);
        log.info("outputStr value:{}",outputStr);
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            log.info("jsonObject:{}",jsonObject);
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }
}
