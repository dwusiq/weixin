package com.wusiq.weixin.dto.req;

/**
 * Created by wicker on 2017/4/12.
 */
public class CommonButton extends BaseButton{
   private BaseButton[] sub_button;

    public BaseButton[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(BaseButton[] sub_button) {
        this.sub_button = sub_button;
    }
}
