package test;

import com.wusiq.weixin.utils.RedisUtils;
import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * Created by wicker on 2017/4/7.
 */
public class Test {
    public static void main(String args[]){
       String ContentDisposition="attachment; filename=1007_d008f0e21c304c1a9042d440d02bdcf3.f10.mp4";
       String FileName = ContentDisposition.substring(ContentDisposition.indexOf("filename=")+"filename=".length(), ContentDisposition.length());
       System.out.println(FileName);
    }
}
