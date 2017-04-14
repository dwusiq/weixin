package com.wusiq.weixin.utils;

import com.wusiq.weixin.base.Constant;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wicker on 2017/4/7.
 */
public class WeixinUtils {
    private static Logger log = LoggerFactory.getLogger(WeixinUtils.class);

    private static final String TIME_OUT = "30000";//超时时间为30秒
    private static final String ACCESS_TOKEN = "accessToken";//redis中保存的access_token的键

    /**
     * 从微信公众号获取信息
     */
    private static InputStream getWeixinInfo(String url){
        InputStream is = null;
        try{
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", TIME_OUT);// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", TIME_OUT); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        }catch (Exception ex){
            try{
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                log.error("关闭流失败",e);
            }
            log.warn("微信请求失败：{}",ex);
        }
        return is;
    }
    /***
     * 获取access_token
     * 返回：流
     */
    public static String getAccess_token() {
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
        url = String.format(url,Constant.WEIXIN_APPID,Constant.WEIXIN_SECRET);

        //请求微信服务
        InputStream is = getWeixinInfo(url);
        String message = null;
        try{
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);
            message = new String(b, "UTF-8");
            log.info("微信服务返回的参数：{}",message);
        }catch(IOException ex) {
            log.warn("系统错误:{}", ex);
        }finally {
            try{
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                log.error("关闭流失败",e);
            }
        }

        JSONObject json = JSONObject.fromObject(message);

        jedis.setex(ACCESS_TOKEN,new Integer(json.getString("expires_in")),json.getString("access_token"));
        accessToken = jedis.get(ACCESS_TOKEN);
        //释放资源
        RedisUtils.returnResource(jedis);
        log.info("返回accessToken:{}",accessToken);

        return accessToken;
    }



    // 上传多媒体文件到微信服务器
    public static final String upload_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    /**
     * 上传多媒体文件到微信服务器<br>
     *
     * @author qincd
     * @date Nov 6, 2014 4:11:22 PM
     */
    public static JSONObject uploadMediaToWX(String filePath,String type,String accessToken) throws IOException{
        File file = new File(filePath);
        if (!file.exists()) {
            log.error("文件不存在！");
            return null;
        }

        String url = upload_media_url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);

        // 请求正文信息

        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // ////////必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(head);

        // 文件正文部分
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        /**
         * 读取服务器响应，必须读取,否则提交不成功
         */
        try {
            // 定义BufferedReader输入流来读取URL的响应
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            conn.disconnect();

            return JSONObject.fromObject(buffer.toString());
        } catch (Exception e) {
            log.error("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }

}
