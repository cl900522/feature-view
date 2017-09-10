package com.siping.wechat.util.qiye;

public class MessageBack {
    private String Encrypt;
    private String MsgSignature;
    private String TimeStamp;
    private String Nonce;
    public String getEncrypt() {
        return Encrypt;
    }
    public void setEncrypt(String encrypt) {
        Encrypt = encrypt;
    }
    public String getMsgSignature() {
        return MsgSignature;
    }
    public void setMsgSignature(String msgSignature) {
        MsgSignature = msgSignature;
    }
    public String getTimeStamp() {
        return TimeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
    public String getNonce() {
        return Nonce;
    }
    public void setNonce(String nonce) {
        Nonce = nonce;
    }

}
