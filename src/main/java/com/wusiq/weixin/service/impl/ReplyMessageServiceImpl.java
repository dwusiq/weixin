package com.wusiq.weixin.service.impl;

import com.wusiq.weixin.base.Constant;
import com.wusiq.weixin.dto.msg.ReplyMessageTextDto;
import com.wusiq.weixin.service.ReplyMessageService;
import com.wusiq.weixin.utils.MessageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 组织返回消息给微信的服务
 */
@Service
public class ReplyMessageServiceImpl implements ReplyMessageService{
    @Override
    public String replyMessageText(Map<String,String> map,String content) {
        //返回的消息
        ReplyMessageTextDto rmtd = new ReplyMessageTextDto();
        rmtd.setToUserName(map.get("FromUserName"));
        rmtd.setFromUserName(map.get("ToUserName"));
        rmtd.setCreateTime(System.currentTimeMillis()+"");
        rmtd.setMsgType(Constant.REQ_MESSAGE_TYPE_TEXT);
        rmtd.setContent(content);
        String respMessage = MessageUtils.ReplyMessageTextDtoToXML(rmtd);
        return respMessage;
    }
}
