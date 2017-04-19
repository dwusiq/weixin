package com.wusiq.weixin.controller;

import com.wusiq.weixin.dto.req.ReqLoginDto;
import com.wusiq.weixin.dto.rsp.RspLoginDto;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wicker on 2017/3/31.
 */

@Component
@RequestMapping("login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value="doLogin.do",method = RequestMethod.POST)
    public String login(ReqLoginDto req, HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");//防止数据传递乱码
        RspLoginDto rsp = new RspLoginDto();
        rsp.setResult("1");
        rsp.setUsername(req.getUsername());


        //转成json字符串
        JSONObject object = JSONObject.fromObject(rsp);
        String res = object.toString();
        System.out.println(res);
        return res;
    }

}
