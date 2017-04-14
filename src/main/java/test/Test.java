package test;

import com.wusiq.weixin.utils.RedisUtils;
import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * Created by wicker on 2017/4/7.
 */
public class Test {
    public static void main(String args[]){
        String originalFile = "H:"+ File.separator+"test"+File.separator+"window.mp4";
        String afterFile = "H:"+ File.separator+"test"+File.separator+"down"+File.separator+"window.mp4";
        File file1 = new File(originalFile);
        File file2 = new File(afterFile);
        InputStream is = null;
        try {
            is = new FileInputStream(file1);
            OutputStream os = new FileOutputStream(file2);
            int len = -1;
            byte[] b = new byte[1024];
            while ((len=is.read(b))!=-1){
                os.write(b,0,len);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }


    }
}
