package com.wusiq.weixin.dto.wacat.req;

/**
 * 创建二维码ticket,二维码详细信息-请求微信服务出参
 */
public class ActionInfoDto {
    private SceneDto scene;

    public SceneDto getScene() {
        return scene;
    }

    public void setScene(SceneDto scene) {
        this.scene = scene;
    }
}
