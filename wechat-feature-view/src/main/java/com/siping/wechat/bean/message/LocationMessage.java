package com.siping.wechat.bean.message;

public class LocationMessage extends CommonMessage implements SendableMessage{

    private String location_X;
    private String location_Y;
    private String scale;
    private String label;

    public String getLocation_X() {
        return location_X;
    }
    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }
    public String getLocation_Y() {
        return location_Y;
    }
    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }
    public String getScale() {
        return scale;
    }
    public void setScale(String scale) {
        this.scale = scale;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String generateXMLContent() throws Exception {
        String xml = "<xml>";
        xml += "<ToUserName><![CDATA[" + super.getToUserName() + "]]></ToUserName>";
        xml += "<FromUserName><![CDATA[" + super.getFromUserName() + "]]></FromUserName>";
        xml += "<CreateTime>" + super.getCreateTimeStr() + "</CreateTime>";
        xml += "<MsgType><![CDATA[" + super.getMsgType() + "]]></MsgType>";
        xml += "<Location_X>" + this.getLocation_X() + "</Location_X>";
        xml += "<Location_Y>" + this.getLocation_Y() + "</Location_Y>";
        xml += "<Scale>" + this.getScale() + "</Scale>";
        xml += "<Label><![CDATA[" + this.getLabel() + "]]></Label>";
        xml += "<MsgId>" + super.getMsgId() + "</MsgId>";
        xml += "</xml>";
        return xml;
    }

}
