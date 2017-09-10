package com.siping.wechat.bean.message;

public enum MessageType {
    text("text"), image("image"), voice("voice"), video("video"), shortvideo("shortvideo"), location("location"), link("link"), event("event");
    private String str;
    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }
    private MessageType(String str){
        this.str = str;
    }
    public static MessageType create(String typeStr) throws Exception {
        MessageType[] values = MessageType.values();
        for (MessageType type : values) {
            if (type.getStr().equalsIgnoreCase(typeStr)) {
                return type;
            }
        }
        throw new Exception();
    }
}
