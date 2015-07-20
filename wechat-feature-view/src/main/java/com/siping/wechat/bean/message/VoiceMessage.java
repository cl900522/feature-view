package com.siping.wechat.bean.message;

public class VoiceMessage extends CommonMessage implements SendableMessage{

    private String MediaId;
    private String Format;
    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getFormat() {
        return Format;
    }
    public void setFormat(String format) {
        Format = format;
    }
    public String generateXMLContent() throws Exception {
        String xml = "<xml>";
        xml += "<ToUserName><![CDATA[" + super.getToUserName() + "]]></ToUserName>";
        xml += "<FromUserName><![CDATA[" + super.getFromUserName() + "]]></FromUserName>";
        xml += "<CreateTime>" + super.getCreateTimeStr() + "</CreateTime>";
        xml += "<MsgType><![CDATA[" + super.getMsgType() + "]]></MsgType>";
        xml += "<MediaId><![CDATA[" + this.getMediaId() + "]]></MediaId>";
        xml += "<Format><![CDATA[" + this.getFormat() + "]]></Format>";
        xml += "<MsgId>" + super.getMsgId() + "</MsgId>";
        xml += "</xml>";
        return xml;
    }

}
