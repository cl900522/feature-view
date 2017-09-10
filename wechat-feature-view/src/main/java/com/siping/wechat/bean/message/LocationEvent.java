package com.siping.wechat.bean.message;

public class LocationEvent extends EventMessage {

    private String latitude;
    private String longitude;
    private String precision;

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getPrecision() {
        return precision;
    }
    public void setPrecision(String precision) {
        this.precision = precision;
    }

}
