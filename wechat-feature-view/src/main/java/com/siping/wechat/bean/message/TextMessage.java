
package com.siping.wechat.bean.message;

public class TextMessage extends CommonMessage implements SendableMessage{

    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String generateXMLContent() throws Exception {
        String xml = "<xml>";
        xml += "<ToUserName><![CDATA[" + super.getToUserName() + "]]></ToUserName>";
        xml += "<FromUserName><![CDATA[" + super.getFromUserName() + "]]></FromUserName>";
        xml += "<CreateTime>" + super.getCreateTimeStr() + "</CreateTime>";
        xml += "<MsgType><![CDATA[" + super.getMsgType() + "]]></MsgType>";
        xml += "<Content><![CDATA[" + this.getContent() + "]]></Content>";
        xml += "<MsgId>" + super.getMsgId() + "</MsgId>";
        xml += "</xml>";
        return xml;
    }
}
