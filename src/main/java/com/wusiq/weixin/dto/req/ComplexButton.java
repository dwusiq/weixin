package com.wusiq.weixin.dto.req;

/**
 * Created by wicker on 2017/4/12.
 */
public class ComplexButton extends BaseButton{
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
