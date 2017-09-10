package com.siping.wechat.bean;

public enum FileType {
    IMAGE("image", 1024), VOICE("voice", 2046), VIDEO("video", 5120);
    private String tag;
    private int fileSize;

    private FileType(String tag, int size){
        this.tag = tag;
        this.fileSize = size;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public int getSize() {
        return fileSize;
    }
    public void setSize(int size) {
        this.fileSize = size;
    }
}
