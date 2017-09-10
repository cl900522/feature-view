package com.siping.wechat.bean;

import org.json.JSONArray;
import org.json.JSONObject;

public class PicTextMessage {
    private MediaFile headImage;
    private String title = "";
    private String author = "";
    private String sourceUrl = "";
    private String content = "";
    private String mediaId;
    private String digest = "";

    public MediaFile getHeadImage() {
        return headImage;
    }
    public void setHeadImage(MediaFile headImage) {
        this.headImage = headImage;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getSourceUrl() {
        return sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getMediaId() {
        return mediaId;
    }
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    public String getDigest() {
        return digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    /**
     *
     * @return
     * @throws Exception
     */
    public String generateSendJson() throws Exception{
        if(this.getMediaId() == null){
            throw new Exception("没有生成的图文消息media_id");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "mpnews");
        JSONObject mpnews = new JSONObject();
        mpnews.put("media_id", this.getMediaId());
        jsonObject.put("mpnews",mpnews);
        JSONObject filter = new JSONObject();
        filter.put("is_to_all", true);
        jsonObject.put("filter", filter);
        return jsonObject.toString();
    }

    public String generateUploadJson() throws Exception {
        if(this.headImage == null || this.headImage.getMediaId() == null){
            throw new Exception("没有生成的消息图片media_id");
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
         JSONObject article = new JSONObject();
         article.put("thumb_media_id",  this.headImage.getMediaId());
         article.put("author", this.getAuthor());
         if(this.getSourceUrl() != null && !this.getSourceUrl().trim().equals("")){
             article.put("content_source_url", this.getSourceUrl());
         }
         article.put("title", this.getTitle());
         article.put("content", this.getContent());
         article.put("show_cover_pic", 1);
         article.put("digest", this.getDigest());
         jsonArray.put(article);
         jsonObject.put("articles", jsonArray);
         return jsonObject.toString();
    }
}
