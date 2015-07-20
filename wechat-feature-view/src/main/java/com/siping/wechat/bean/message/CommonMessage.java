package com.siping.wechat.bean.message;

import java.util.Map;

public abstract class CommonMessage extends Message{
    private String msgId;

    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public static CommonMessage create(Map<String, String> map) throws Exception {
        CommonMessage message = null;
        String msgType = map.get("MsgType").toString();
        if(msgType.equals(MessageType.text.getStr())){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent(map.get("Content").toString());
            textMessage.setMsgId(map.get("MsgId").toString());
            message = textMessage;
        }else if (msgType.equals(MessageType.image.getStr())){
            ImageMessage imageMessage = new ImageMessage();
            imageMessage.setPicUrl(map.get("PicUrl").toString());
            imageMessage.setMediaId(map.get("MediaId").toString());
            message = imageMessage;
        }else if (msgType.equals(MessageType.voice.getStr())){
            VoiceMessage voiceMessage = new VoiceMessage();
            voiceMessage.setFormat(map.get("Format").toString());
            voiceMessage.setMediaId(map.get("MediaId").toString());
            message = voiceMessage;
        }else if (msgType.equals(MessageType.video.getStr())){
            VideoMessage videoMessage = new VideoMessage();
            videoMessage.setThumbMediaId(map.get("ThumbMediaId").toString());
            videoMessage.setMediaId(map.get("MediaId").toString());
            message = videoMessage;
        }else if (msgType.equals(MessageType.shortvideo.getStr())){
            ShortVideoMessage shortVideoMessage = new ShortVideoMessage();
            shortVideoMessage.setThumbMediaId(map.get("ThumbMediaId").toString());
            shortVideoMessage.setMediaId(map.get("MediaId").toString());
            message = shortVideoMessage;
        }else if (msgType.equals(MessageType.location.getStr())){
            LocationMessage locationMessage = new LocationMessage();
            locationMessage.setLocation_X(map.get("Location_X").toString());
            locationMessage.setLocation_Y(map.get("Location_Y").toString());
            locationMessage.setScale(map.get("Scale").toString());
            locationMessage.setLabel(map.get("Label").toString());
            message = locationMessage;
        }else if (msgType.equals(MessageType.link.getStr())){
            LinkMessage linkMessage = new LinkMessage();
            linkMessage.setTitle(map.get("Title").toString());
            linkMessage.setDescription(map.get("Description").toString());
            linkMessage.setUrl(map.get("Url").toString());
            message = linkMessage;
        }else if (msgType.equals(MessageType.location.getStr())){
            LocationMessage locationMessage = new LocationMessage();
            locationMessage.setLocation_X(map.get("Location_X").toString());
            locationMessage.setLocation_Y(map.get("Location_Y").toString());
            locationMessage.setScale(map.get("Scale").toString());
            locationMessage.setLabel(map.get("Label").toString());
            message = locationMessage;
        }
        message.setToUserName(map.get("ToUserName").toString());
        message.setFromUserName(map.get("FromUserName").toString());
        message.setCreateTime(Long.parseLong(map.get("CreateTime").toString()));
        message.setMsgType(MessageType.create(map.get("MsgType").toString()));
        message.setMsgId(map.get("MsgId").toString());
        return message;
    }
}
