package com.wusiq.weixin.controller;

import com.wusiq.weixin.dto.req.*;
import com.wusiq.weixin.dto.rsp.RspDto;
import com.wusiq.weixin.dto.rsp.RspLoginDto;
import com.wusiq.weixin.service.WeiXinService;
import com.wusiq.weixin.utils.StringTools;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wicker on 2017/4/1.
 */
@Component
@RequestMapping("weixinManage")
public class WeiXinController {

    @Autowired
    WeiXinService WeiXinServiceImpl;
    private static final String TOKEN = "wicker";
    private Logger log = LoggerFactory.getLogger(WeiXinController.class);


    @ResponseBody
    @RequestMapping(value = "serviceFeedback.do", method = RequestMethod.GET)
    public String login(ReqServiceFeedbackDto req, HttpServletResponse response) {
        //response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        //入参
         String signature = req.getSignature();
         String timestamp = req.getTimestamp();
         String nonce = req.getNonce();
         String echostr = req.getEchostr();
         log.info("微信服务器回调的参数:signature={},timestamp={},nonce={},echostr={}",signature,timestamp,nonce,echostr);

         //是否校验通过
        boolean ischeckPass = checkSignature(signature,timestamp,nonce);
        log.info("微信服务器校验结果：{}",ischeckPass);

        if(ischeckPass){
            return echostr;
        }

        return "qiang love jun";
    }

    @ResponseBody
    @RequestMapping(value = "serviceFeedback.do", method = RequestMethod.POST)
    public String processRequest(HttpServletRequest request, HttpServletResponse response) {
   /*     try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");*/

        String rspStr = WeiXinServiceImpl.processRequest(request);
        return rspStr;
    }

    @ResponseBody
    @RequestMapping(value="test.do",method = RequestMethod.GET)
    public String login(){
      //  String rspStr = "伍思强 爱 伍柳君";
        String rspStr = "aaaa";

        Menu menu = getMenu();

        rspStr = WeiXinServiceImpl.getServiceIp();



        RspLoginDto rsp = new RspLoginDto();
        rsp.setResult(rspStr);
        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        log.info("rsp:{}",res);
        return rspStr;
    }

    /***
     * 上传临时素材
     */
    @ResponseBody
    @RequestMapping(value="uploadMedia.do",method = RequestMethod.GET)
    public String uploadMedia(){
        String rspStr = "aaaa";
        //String filePath = "H:"+ File.separator+"test"+File.separator+"scream.JPG";
        //String filePath = "H:"+ File.separator+"test"+File.separator+"cup.mp4";
        String filePath = "H:"+ File.separator+"test"+File.separator+"scream01.JPG";

        JSONObject jobj = WeiXinServiceImpl.uploadMedia(filePath,"image");
        rspStr = jobj.getString("media_id");
        log.info("rspStr:{}",rspStr);

        RspLoginDto rsp = new RspLoginDto();
        rsp.setResult(rspStr);
        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        log.info("rsp:{}",res);
        return rspStr;
    }


    /***
     * 获取临时素材
     */
    @ResponseBody
    @RequestMapping(value="downloadFile.do",method = RequestMethod.GET)
    public String downloadFile(){
        String rspStr = "aaaa";
        String filePath = "H:"+ File.separator+"test"+File.separator+"Files";
      // String mediaId = "4aTHSQywlN2NfmhCDkq93R4HJaBOoTQObis3H308PdqFbdQ2raXt78r7uOCWK-LU";
        //String mediaId = "kyMUfUSVWeeqiAVFBS6tqN6Bft7P4io4V2cFW0RReO6imrrmoZS9IBWfjy1X0NWP";//scream01.JPG
        String mediaId = "BZbBW-v_-uPObjr0kah2AnNiEQ6a-oKy_cGdrOkKZdTjcS__nqfQsSQ03j8SSIdR";//cup.mp4

        rspStr = WeiXinServiceImpl.downloadMedia(mediaId,filePath);
        log.info("rspStr:{}",rspStr);

        RspDto rsp = new RspDto();
        rsp.setResult(rspStr);
        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        log.info("rsp:{}",res);
        return rspStr;
    }


    /***
     * 获取微信视频素材
     */
    @ResponseBody
    @RequestMapping(value="downloadVideo.do",method = RequestMethod.GET)
    public String downloadVideo(){
        String rspStr = "aaaa";
        String filePath = "H:"+ File.separator+"test"+File.separator+"Files";
        String mediaId = "BZbBW-v_-uPObjr0kah2AnNiEQ6a-oKy_cGdrOkKZdTjcS__nqfQsSQ03j8SSIdR";//cup.mp4
        rspStr = WeiXinServiceImpl.downloadVideo(mediaId,filePath);
        log.info("rspStr:{}",rspStr);

        RspDto rsp = new RspDto();
        rsp.setResult(rspStr);
        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        log.info("rsp:{}",res);
        return rspStr;
    }




    /**
     *对请求进行校验
     */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        MessageDigest md = null;
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        //排序
        Arrays.sort(arr);

        //拼接成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            log.warn("加密失败",e);
            return false;
        }
        //SHA-1加密
        byte[] digest = md.digest(content.toString().getBytes());
        String tmpStr = StringTools.byteToStr(digest);

        // 比对 判断
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    private Menu getMenu(){
        ComplexButton cb11 = new ComplexButton();
        cb11.setName("公交车");
        cb11.setType("click");
        cb11.setKey("11");
        ComplexButton cb12 = new ComplexButton();
        cb12.setName("地铁");
        cb12.setType("click");
        cb12.setKey("12");
        ComplexButton cb13 = new ComplexButton();
        cb13.setName("自行车");
        cb13.setType("click");
        cb13.setKey("13");

        ComViewButton vcb21 = new ComViewButton();
        vcb21.setName("登录");
        vcb21.setType("view");
        vcb21.setUrl("http://dwusiq.tunnel.qydev.com/weixin/index.jsp");
        ComViewButton cvb22 = new ComViewButton();
        cvb22.setName("欢迎");
        cvb22.setType("view");
        cvb22.setUrl("http://dwusiq.tunnel.qydev.com/weixin/welcome.html");
        ComplexButton cb22 = new ComplexButton();
        cb22.setName("鸡肉");
        cb22.setType("click");
        cb22.setKey("22");
        ComplexButton cb23 = new ComplexButton();
        cb23.setName("鸭肉");
        cb23.setType("click");
        cb23.setKey("23");
        ComplexButton cb24 = new ComplexButton();
        cb24.setName("白菜");
        cb24.setType("click");
        cb24.setKey("24");

        ComplexButton cb31 = new ComplexButton();
        cb31.setName("长裤");
        cb31.setType("click");
        cb31.setKey("31");
        ComplexButton cb32 = new ComplexButton();
        cb32.setName("白裙");
        cb32.setType("click");
        cb32.setKey("32");
        ComplexButton cb33 = new ComplexButton();
        cb33.setName("花鞋");
        cb33.setType("click");
        cb33.setKey("33");
        ComplexButton cb34 = new ComplexButton();
        cb34.setName("红帽");
        cb34.setType("click");
        cb34.setKey("34");
        ComplexButton cb35 = new ComplexButton();
        cb35.setName("手套");
        cb35.setType("click");
        cb35.setKey("35");


        CommonButton comb11 = new CommonButton();
        comb11.setName("交通");
        comb11.setSub_button(new BaseButton[]{cb11,cb12,cb13});

        CommonButton comb22 = new CommonButton();
        comb22.setName("饮食");
        comb22.setSub_button(new BaseButton[]{vcb21,cvb22,cb23,cb24});

        CommonButton comb33 = new CommonButton();
        comb33.setName("服饰");
        comb33.setSub_button(new BaseButton[]{cb31,cb32,cb33,cb34,cb35});

        Menu menu = new Menu();
        menu.setButton(new BaseButton[]{comb11,comb22,comb33});

        return menu;
    }
}
