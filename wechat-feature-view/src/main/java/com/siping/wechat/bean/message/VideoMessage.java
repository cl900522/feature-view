package com.siping.wechat.bean.message;

public class VideoMessage extends CommonMessage implements SendableMessage{

    private String mediaId;
    private String thumbMediaId;
    public String getMediaId() {
        return mediaId;
    }
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    public String getThumbMediaId() {
        return thumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
    public String generateXMLContent() throws Exception {
        String xml = "<xml>";
        xml += "<ToUserName><![CDATA[" + super.getToUserName() + "]]></ToUserName>";
        xml += "<FromUserName><![CDATA[" + super.getFromUserName() + "]]></FromUserName>";
        xml += "<CreateTime>" + super.getCreateTimeStr() + "</CreateTime>";
        xml += "<MsgType><![CDATA[" + super.getMsgType() + "]]></MsgType>";
        xml += "<MediaId><![CDATA[" + this.getMediaId() + "]]></MediaId>";
        xml += "<ThumbMediaId><![CDATA[" + this.getThumbMediaId() + "]]></ThumbMediaId>";
        xml += "<MsgId>" + super.getMsgId() + "</MsgId>";
        xml += "</xml>";
        return xml;
    }

}
