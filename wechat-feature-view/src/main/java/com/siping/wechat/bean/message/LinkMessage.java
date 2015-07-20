package com.siping.wechat.bean.message;

public class LinkMessage extends CommonMessage implements SendableMessage{

    private String title;
    private String description;
    private String url;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String generateXMLContent() throws Exception {
        String xml = "<xml>";
        xml += "<ToUserName><![CDATA[" + super.getToUserName() + "]]></ToUserName>";
        xml += "<FromUserName><![CDATA[" + super.getFromUserName() + "]]></FromUserName>";
        xml += "<CreateTime>" + super.getCreateTimeStr() + "</CreateTime>";
        xml += "<MsgType><![CDATA[" + super.getMsgType() + "]]></MsgType>";
        xml += "<Title><![CDATA[" + this.getTitle() + "]]></Title>";
        xml += "<Description><![CDATA[" + this.getDescription() + "]]></Description>";
        xml += "<Url><![CDATA[" + this.getUrl() + "]]></Url>";
        xml += "<MsgId>" + super.getMsgId() + "</MsgId>";
        xml += "</xml>";
        return xml;
    }

}
