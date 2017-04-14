package com.wusiq.weixin.service.impl;

import com.wusiq.weixin.base.Constant;
import com.wusiq.weixin.dto.msg.ReplyMessageTextDto;
import com.wusiq.weixin.service.MessageService;
import com.wusiq.weixin.service.ReplyMessageService;
import com.wusiq.weixin.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信的消息请求处理服务
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    ReplyMessageService ReplyMessageServiceImpl;

    /**
     * 处理文本消息请求
     */
    @Override
     public String messageTextDo(Map<String,String> map){
        String Content = map.get("Content");
        log.info("request content:{}",Content);

        String rspMessage = "欢迎光临，本店的服务超多，涵盖衣食住行等各领域，请输入您需要的服务编号：洗头（001），炒菜(002)" +
                "，购物（003），租房（004）,旅行（005）";
        if("001".equals(Content)){
            rspMessage = "感谢您的支持，您选择了价值200元超低价超实惠的霸王洗发液洗头服务一次,请耐心等候，我店员工将" +
                    "尽快为您服务！";
        }else if("002".equals(Content)){
            rspMessage = "感谢您的支持，您选择了价值0.5元的超香鸡蛋炒饭一份,请耐心等候，我店员工已去买鸡蛋！";
        }else if("003".equals(Content)){
            rspMessage = "感谢您的支持，您购买了500元的10分钟摇摇车体验卷一份,您可去我店门口体验了！";
        }else if("004".equals(Content)){
            rspMessage = "感谢您的支持，您选择了价值3000万海南省三亚市亚龙湾超大型精装修，舒适，健康的竹筏一艘！";
        }else if("005".equals(Content)){
            rspMessage = "感谢您的支持，您选择了价值5000元的世华一日游，祝旅行快乐！";
        }

        //返回的消息
        String respMessage = ReplyMessageServiceImpl.replyMessageText(map,rspMessage);
        return respMessage;
    }

    /**
     * 处理地理位置消息请求
     */
    @Override
    public String messageLocationDo(Map<String, String> map) {
         String context = "您的具体位置是：%1s(纬度%2s，经度%3s)";
         String Location_X = map.get("Location_X");
         String Location_Y = map.get("Location_Y");
         String Label = map.get("Label");
         String rspMessage = String.format(context, Label,Location_X,Location_Y);

        //返回的消息
        String respMessage = ReplyMessageServiceImpl.replyMessageText(map,rspMessage);
        return respMessage;
    }

}
