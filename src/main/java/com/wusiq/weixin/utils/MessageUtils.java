package com.wusiq.weixin.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wusiq.weixin.dto.msg.ReplyMessageTextDto;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wicker on 2017/4/13.
 */
public class MessageUtils {
    private static Logger log = LoggerFactory.getLogger(MessageUtils.class);

    /**
     * 解析微信发来的请求（XML）
     */
    public static Map<String, String> readXML(HttpServletRequest request){
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        try {
            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            // 得到xml根元素
            Element root = document.getRootElement();
            //获得所有元素
            List<Element> list = root.elements();

            for (Element element:list) {
                map.put(element.getName(),element.getText());
            }
        } catch (IOException e) {
            log.warn("readXML fail",e);
        } catch (DocumentException e) {
            log.warn("readXML fail",e);
        }
        return map;
    }

    /**
     * 文本消息对象转换成xml
     */
    public static String ReplyMessageTextDtoToXML(ReplyMessageTextDto replyMessageTextDto){
        xstream.alias("xml", replyMessageTextDto.getClass());
        String result = xstream.toXML(replyMessageTextDto);
        log.info("ReplyMessageTextDtoToXML result:{}",result);
        return result;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
