package com.wusiq.weixin.controller;

import com.wusiq.weixin.dto.req.ReqLoginDto;
import com.wusiq.weixin.dto.req.ReqQueryMessageDto;
import com.wusiq.weixin.dto.rsp.RspLoginDto;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登陆请求
 */
@Component
@RequestMapping("login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value="doLogin.do",method = RequestMethod.POST)
    public String login(ReqLoginDto req, HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码

        //存储到session
        HttpSession session = request.getSession();
        session.setAttribute("userName",req.getUsername());


        RspLoginDto rsp = new RspLoginDto();
        rsp.setResult("1");
        rsp.setUsername(req.getUsername());


        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        System.out.println(res);
        return res;
    }

    /***
     * 信息查询
     */
    @ResponseBody
    @RequestMapping(value="queryMessage.do",method = RequestMethod.POST)
    public String queryMessage(ReqQueryMessageDto req,HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        log.info("LoginController.");
        String userName = (String) request.getSession().getAttribute("userName");
        log.info("LoginController.queryMessage() userName:{}",userName);

        RspLoginDto rsp = new RspLoginDto();
        rsp.setResult("1");
        rsp.setUsername(req.getCondition());


        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        System.out.println(res);
        return res;
    }




}
