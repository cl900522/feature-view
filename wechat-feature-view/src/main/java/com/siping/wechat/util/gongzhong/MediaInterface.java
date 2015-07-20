package com.siping.wechat.util.gongzhong;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.json.JSONObject;

import com.siping.wechat.WeChatConstant;
import com.siping.wechat.bean.MediaFile;
import com.siping.wechat.bean.MyX509TrustManager;
import com.siping.wechat.bean.PicTextMessage;
import com.siping.wechat.bean.WeChatAccount;
import com.siping.wechat.util.WeChatUtil;

/**
 * 素材管理接口
 * @author nil
 *
 */
public class MediaInterface {

    /**
     * 向微信服务器上传文件,并返回media_id
     * @param wechatAccount wechat 帐号信息，accesstoken已获取
     * @param medicaFile 多媒体文件
     */
    public static String addMateria(WeChatAccount wechatAccount, MediaFile mediaFile) throws Exception {
        String action = WeChatConstant.ADD_MATERIAL;
        action = action.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken()).replace("FILE_TYPE", mediaFile.getFileType().getTag());

        /**
         * 创建SSLContext对象
         */
        TrustManager[] tm = { new MyX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        /**
         * 从上述SSLContext对象中得到SSLSocketFactory对象
         */
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        HttpsURLConnection con = null;
        URL url = new URL(action);
        con = (HttpsURLConnection) url.openConnection();
        con.setSSLSocketFactory(ssf);

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);

        con.setDoInput(true);
        con.setDoOutput(true);
        // post方式不能使用缓存
        con.setUseCaches(false);

        String result = null;
        File file = new File(mediaFile.getFilePath());
        if (!file.exists() || !file.isFile()) {
            throw new Exception("上传的文件不存在");
        }

        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;id=\"media\";name=\"media\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("上传媒体文件出错:" + e.getMessage());
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = new JSONObject(result);
        if(!jsonObj.has("media_id")){
            throw new Exception("上传文件失败");
        }else{
            String mediaId = jsonObj.getString("media_id");
            mediaFile.setMediaId(mediaId);
            return mediaId;
        }
    }

    /**
     * 向微信服务器上传文件,并返回media_id
     * @param accessToken 进入的接口
     * @param type 文件类型(语音或者是图片) 分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return json 的格式
     * {
     *  "media_id":"nrSKG2eY1E_svLs0Iv2Vvh46PleKk55a47cNO1ZS5_pdiNiSXuijbCmWWc8unzBQ",
     *  "created_at":1408436207,
     *  "type":"image"
     * }
     */
    @Deprecated
    public static String uploadFile(WeChatAccount wechatAccount, MediaFile mediaFile) throws Exception {
        String action = WeChatConstant.UPLOAD_FILE;
        action = action.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken()).replace("FILE_TYPE", mediaFile.getFileType().getTag());

        URL url = new URL(action);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // 以Post方式提交表单，默认get方式
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        // post方式不能使用缓存
        con.setUseCaches(false);

        String result = null;
        File file = new File(mediaFile.getFilePath());
        if (!file.exists() || !file.isFile()) {
            throw new Exception("上传的文件不存在");
        }

        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("上传媒体文件出错:" + e.getMessage());
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = new JSONObject(result);
        if(!jsonObj.has("media_id")){
            throw new Exception("上传文件失败");
        }else{
            String mediaId = jsonObj.getString("media_id");
            mediaFile.setMediaId(mediaId);
            return mediaId;
        }
    }

    /**
     * 上传图文消息
     * @param wechatAccount
     * @param message
     * @return TODO
     * @throws Exception
     */
    public static String uploadMessage(WeChatAccount wechatAccount, PicTextMessage message) throws Exception{
        String url = WeChatConstant.UPLOAD_MESSAGE;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());

        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, "POST", message.generateUploadJson());
        try {
            if(!jsonObject.has("media_id")){
                throw new Exception("上传图文消息失败");
            }else{
                message.setMediaId(jsonObject.getString("media_id"));
                return jsonObject.getString("media_id");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送图文消息
     * @param wechatAccount
     * @param message
     * @throws Exception
     */
    public static void sendAllMessege(WeChatAccount wechatAccount, PicTextMessage message) throws Exception{
        String url = WeChatConstant.SEND_ALL_NEWS;
        url = url.replace("ACCESS_TOKEN", wechatAccount.getAccessToken().getToken());

        JSONObject jsonObject = WeChatUtil.sendHttpRequestAndParseResultToJsonobject(url, "POST", message.generateSendJson());
        try {
            if(jsonObject.getInt(WeChatConstant.JSON_ERRCODE_KEY) != 0){
                throw new Exception(jsonObject.getString(WeChatConstant.JSON_ERRMSG_KEY));
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
