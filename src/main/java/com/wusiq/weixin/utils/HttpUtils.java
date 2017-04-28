package com.wusiq.weixin.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * http请求工具类
 */
public class HttpUtils {
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static final String ENCODING="UTF-8";//编码格式
    private static final String TIME_OUT = "30000";// 超时30秒

    /**
     * 描述：上传文件
     * @param url  请求地址
     * @param filePath 文件地址
     * @return
     */
    public static JSONObject uploadFile(String url,String filePath) {
        log.info("uploadFile param:[url:{},filePath:{}]",url,filePath);

        //非空判断
        if(StringUtils.isEmpty(url)){
            log.warn("uploadFile fail:url is empty");return null;
        }
        if(StringUtils.isEmpty(filePath)){
            log.warn("uploadFile fail:filePath is empty");return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            log.warn("File not found！");
            return null;
        }

        HttpURLConnection conn = null;
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn = (HttpURLConnection)HttpUtils.getUrlConnection(url);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset",ENCODING);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // ////////必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        try {
            byte[] head = sb.toString().getBytes(ENCODING);
            // 获得输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(head);

            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(ENCODING);// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            log.warn("uploadFile fail",e);
            return null;
        } catch (IOException e) {
            log.warn("uploadFile fail",e);
            return null;
        }

        //读取服务器响应，必须读取,否则提交不成功
        try {
            // 定义BufferedReader输入流来读取URL的响应
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            conn.disconnect();
            String jsonStr = buffer.toString();
            log.info("uploadFile result：{}",jsonStr);
            return JSONObject.fromObject(jsonStr);
        } catch (Exception e) {
            log.warn("uploadFile fail",e);
            return null;
        }

    }

    /**
     * 描述：下载文件
     * @param requestUrl 请求地址
     * @param fileSavePath 保存位置
     * @return
     */
    public static String downloadFile(String requestUrl,String fileSavePath){
        log.info("downloadFile param:[requestUrl:{},fileSavePath:{}]",requestUrl,fileSavePath);
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        BufferedOutputStream bos = null;

        //非空判断
        if(StringUtils.isEmpty(requestUrl)){
            log.warn("downloadFile fail:requestUrl is empty");return null;
        }
        if(StringUtils.isEmpty(fileSavePath)){
            log.warn("downloadFile fail:fileSavePath is empty");return null;
        }

        File dir = new File(fileSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!fileSavePath.endsWith(File.separator)) {
            fileSavePath += File.separator;
        }
        try{
            conn = (HttpURLConnection)HttpUtils.getUrlConnection(requestUrl);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if(200!=responseCode){
                log.warn("downloadFile fail,responseCode:{}",responseCode);
                return null;
            }

            inputStream = conn.getInputStream();
            String ContentDisposition = conn.getHeaderField("Content-disposition");
            log.info("ContentDisposition:{}",ContentDisposition);
            if(StringUtils.isEmpty(ContentDisposition)){
                log.warn("ContentDisposition is empty");
                return null;
            }
            ContentDisposition = ContentDisposition.replace("\"","");
            String FileName = ContentDisposition.substring(ContentDisposition.indexOf("filename=")+"filename=".length(), ContentDisposition.length());
            fileSavePath += FileName;
            log.info("meida fileSavePath:{}",fileSavePath);
            bos = new BufferedOutputStream(new FileOutputStream(fileSavePath));
            byte[] data = new byte[1024];
            int len = -1;

            while ((len = inputStream.read(data)) != -1) {
                bos.write(data,0,len);
            }
        }catch(Exception e){
            log.warn("downloadFile fail",e);
            return null;
        }finally{
            try{
                if(inputStream != null){
                    inputStream.close();
                }
                if(bos != null){
                    bos.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            }catch (IOException ex){
                log.warn("resourceClose fail",ex);
            }
        }
        return fileSavePath;
    }

    /***
     * 描述:  发起https请求并获取结果
     * @param requestMethod GET、POST
     * @param outputStr 参数
     * @param requestUrl 请求地址
     * @return
     */
    public static JSONObject httpsRequest(String requestMethod, String outputStr,String requestUrl) {
        log.info("发起https请求并获取结果.开始.入参:[requestMethod:{},outputStr:{},requestUrl:{}]",requestMethod,outputStr,requestUrl);
        HttpsURLConnection httpsConn = null;

        //非空判断
        if(StringUtils.isEmpty(requestMethod)){
            log.warn("发起https请求并获取结果.异常:requestMethod is empty");return null;
        }
        if(StringUtils.isEmpty(requestUrl)){
            log.warn("发起https请求并获取结果.异常:requestUrl is empty");return null;
        }

        try{
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpsConn = (HttpsURLConnection)HttpUtils.getUrlConnection(requestUrl);//https传输协议
            httpsConn.setSSLSocketFactory(ssf);
            httpsConn.setRequestMethod(requestMethod);// 设置请求方式（GET/POST）

            if ("GET".equalsIgnoreCase(requestMethod)){
                httpsConn.connect();
            }

            int responseCode = httpsConn.getResponseCode();
            if(200!=responseCode){
                log.warn("发起https请求并获取结果.异常,responseCode:{}",responseCode);
            }

            return urlConnectionHandle(httpsConn,outputStr);
        } catch (IOException e) {
            log.warn("httpsRequest",e);
        } catch (NoSuchAlgorithmException e) {
            log.warn("httpsRequest",e);
        }  catch (NoSuchProviderException e) {
            log.warn("httpsRequest",e);
        } catch (KeyManagementException e) {
            log.warn("httpsRequest",e);
        }finally {
            // 释放资源
            if(httpsConn != null){
                httpsConn.disconnect();
            }
        }
        return null;
    }

    /**
     * 描述：发起http请求并获取结果
     * @param requestMethod  GET、POST
     * @param requestUrl  请求地址
     * @return
     */
    public static JSONObject httpRequest(String requestMethod, String outputStr, String requestUrl) {
        log.info("发起http请求并获取结果.开始.入参:[requestMethod:{},outputStr:{},requestUrl:{}]",requestMethod,outputStr,requestUrl);
        HttpURLConnection httpConn = null;

        //非空判断
        if(StringUtils.isEmpty(requestMethod)){
            log.warn("发起http请求并获取结果.异常:requestMethod is empty");return null;
        }
        if(StringUtils.isEmpty(requestUrl)){
            log.warn("发起http请求并获取结果.异常:requestUrl is empty");return null;
        }

        try {
            httpConn = (HttpURLConnection)HttpUtils.getUrlConnection(requestUrl);//http传输协议
            httpConn.setRequestMethod(requestMethod);// 设置请求方式（GET/POST）
            return urlConnectionHandle(httpConn,outputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            // 释放资源
            if(httpConn != null){
                httpConn.disconnect();
            }
        }
        return null;
    }

    /***
     * 获取URL连接实例
     */
    private static URLConnection getUrlConnection(String requestUrl){
        URLConnection  conn = null;
        try {
            URL url = new URL(requestUrl);
            conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);//设置不缓存
            System.setProperty("sun.net.client.defaultConnectTimeout", TIME_OUT);
            System.setProperty("sun.net.client.defaultReadTimeout", TIME_OUT);
        } catch (MalformedURLException e) {
            log.warn("获取URL连接实例 异常",e);
        } catch (IOException e) {
            log.warn("获取URL连接实例 异常",e);
        }
        return conn;
    }

    /***
     * url连接处理(发送参数和接收返回值)
     */
    private static JSONObject urlConnectionHandle(URLConnection urlConnection,String outputStr) {
        JSONObject jsonObject =null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;

        try{
            // 当有数据需要提交时
            if (StringUtils.isNotEmpty(outputStr)) {
                OutputStream outputStream = urlConnection.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes(ENCODING));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            inputStream = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, ENCODING);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer  = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            String urlBakStr = buffer.toString();
            log.info("url连接处理(发送参数和接收返回值)，得到的返回参:{}",urlBakStr);
            jsonObject = JSONObject.fromObject(urlBakStr);
        } catch (IOException e) {
            log.warn("url连接处理(发送参数和接收返回值).异常",e);
        }finally {
            // 释放资源
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(inputStreamReader != null){
                    inputStreamReader.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                log.warn("发送url请求.异常",e);
            }
        }
        return jsonObject;
    }


}
