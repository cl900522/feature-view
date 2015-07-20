package com.siping.wechat.bean.message;

import java.util.Date;
import java.util.Map;

public class Message {
    private String toUserName;
    private String fromUserName;
    private MessageType msgType;
    private Date createTime;

    public String getToUserName() {
        return toUserName;
    }
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    public String getFromUserName() {
        return fromUserName;
    }
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
    public MessageType getMsgType() {
        return msgType;
    }
    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }
    public static Message create(Map<String, String> map) throws Exception {
        String msgType = map.get("MsgType").toString();
        if(!msgType.equals("event")){
            return CommonMessage.create(map);
        }else{
            return EventMessage.create(map);
        }
    }
    public Date getCreatTime(){
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = new Date(createTime * 1000);
    }
    public Long getCreateTimeStr() {
        if(this.createTime == null){
            return new Long(0);
        }
        return this.createTime.getTime()/1000;
    }
}
