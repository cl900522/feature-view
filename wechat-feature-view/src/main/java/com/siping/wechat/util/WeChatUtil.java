package com.siping.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import com.siping.wechat.bean.MyX509TrustManager;
import com.siping.wechat.bean.QiyeAccount;
import com.siping.wechat.bean.WeChatAccount;

/**
 * 微信工具类
 * @author: LHQ
 * @date: 2014年5月26日 下午5:08:48
 * @email: mingxuan.chen@sipingsoft.com
 * @function:
 * @version :
 */
public class WeChatUtil {
    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 检查签名，用于微信设置url时认证
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(WeChatAccount wechatAccount, String signature, String timestamp, String nonce) {
        String[] arr = new String[] { wechatAccount.getToken(), timestamp, nonce };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            /**
             * 将三个参数字符串拼接成一个字符串进行sha1加密
             */
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 检查企业号签名，用于微信设置url时认证
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkQiyeSignature(QiyeAccount qiyeAccount, String signature, String timestamp, String nonce) {
        String[] arr = new String[] { qiyeAccount.getToken(), timestamp, nonce };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            /**
             * 将三个参数字符串拼接成一个字符串进行sha1加密
             */
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 访问url并将返回值转换为Json对象（向微信服务器发送的url请求主要通过该方法调用）
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static JSONObject sendHttpRequestAndParseResultToJsonobject(String requestUrl, String requestMethod, String outputStr) throws Exception{
        JSONObject jsonObject = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection httpUrlConn = null;
        try {
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

            URL url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            /**
             * 设置请求方式（GET/POST）
             */
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            /**
             * 当有数据需要提交时
             */
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            /**
             * 将返回的输入流转换成字符串
             */
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            jsonObject = new JSONObject(buffer.toString());
        } catch (Exception e) {
            throw e;
        } finally {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(inputStreamReader != null){
                inputStreamReader.close();
            }
            if(inputStream != null){
                inputStream.close();
            }
            if(httpUrlConn != null){
                httpUrlConn.disconnect();
            }
        }
        return jsonObject;
    }

    /**
     * 将微信客户端发送的消息（包括点击菜单）的xml解析为map类型，
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseMessageXmlToMap(InputStream inputStream) throws Exception {
        Map<String, String> map = null;
        try{
            Document document = new SAXReader().read(inputStream);
            Element root = document.getRootElement();
            map = new HashMap<String, String>();
            for (Element e : (List<Element>) root.elements()) {
                map.put(e.getName(), e.getText());
            }
        }catch(Exception e){
            throw e;
        }
        finally{
            if(inputStream != null){
                inputStream.close();
            }
        }
        return map;
    }
}
