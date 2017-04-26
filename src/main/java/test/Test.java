package test;

import com.wusiq.weixin.dto.msg.BaseDto;
import com.wusiq.weixin.utils.RedisUtils;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * Created by wicker on 2017/4/7.
 */
public class Test {
    public static void main(String args[]){
        BaseDto bd = new BaseDto();
        bd.setMsgType("1");
        bd.setCreateTime("adfasd");
        bd.setFromUserName("ff");

        JSONObject jsonObj = JSONObject.fromObject(bd);

        BaseDto obj = (BaseDto)JSONObject.toBean(jsonObj,BaseDto.class);

        System.out.println(obj.getCreateTime());

    }
}
