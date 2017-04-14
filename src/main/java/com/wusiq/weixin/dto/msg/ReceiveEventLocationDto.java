package com.wusiq.weixin.dto.msg;

/**
 * 接收事件实体类-上报地理位置
 */
public class ReceiveEventLocationDto extends ReceiveEventBaseDto{
    private String Latitude;
    private String Longitude;
    private String Precision;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }
}
