package com.wusiq.weixin.dto.wacat.req;

/**
 * 创建二维码ticket,场景值ID-请求微信服务出参
 * 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
 */
public class SceneDto {
    private Integer scene_id;

    public Integer getScene_id() {
        return scene_id;
    }

    public void setScene_id(Integer scene_id) {
        this.scene_id = scene_id;
    }
}
