package com.clinix.askmobile.webservice.impl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.graphics.Bitmap;
import org.kobjects.base64.Base64;

import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.ProcessResult;
import com.clinix.askmobile.core.bean.Reply;
import com.clinix.askmobile.core.bean.Subject;
import com.clinix.askmobile.core.bean.UserInfo;
import com.clinix.askmobile.core.bean.VersionInfo;
import com.clinix.askmobile.webservice.WebServiceProxy;

public class WebServiceProxyImpl implements WebServiceProxy {
    /**
     * web service参数
     */
    private final static String nameSpace = "http://webservice.clinix.com/";
    /**
     * 发布时需要修改ip或域名
     */
    private final static String endPoint = "http://192.168.1.4:8080/webservice";
    /**
     * 第一个参数的名称
     */
    private static final String ARGS01 = "arg0";

    @Override
    public VersionInfo getLatestVersion() throws Exception {
        VersionInfo version = new VersionInfo();
        final String methodName = "getLatestVersion";
        final String soapAction = "";

        SoapObject rpc = new SoapObject(nameSpace, methodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);

        version.fromJson(callResult.getMessage());
        return version;
    }

    @Override
    public String uploadHistoryRecord(HistoryRecord history) throws Exception {
        final String methodName = "uploadHistoryRecord";
        final String soapAction = "";

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, history.toJson());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);

        return callResult.getMessage();
    }

    @Override
    public String submitSubject(Subject subject) throws Exception {
        final String methodName = "submitSubject";
        final String soapAction = "";

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, subject.toJson());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);
        return callResult.getMessage();
    }

    @Override
    public List<Subject> querySubject(UserInfo user, String tag) throws Exception {
        List<Subject> subjectList = new ArrayList<Subject>();
        final String methodName = "querySubject";
        final String soapAction = "";

        /**
         * wrap params as json format
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", user.getId());
            jsonObject.put("isOver", tag.equals("OVER"));
        } catch (Exception e) {
            throw new Exception("Error to organize param!");
        }

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, jsonObject.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);

        JSONArray jsonArray = new JSONArray(callResult.getMessage());
        for (int i = 0; i < jsonArray.length(); i++) {
            Subject subject = new Subject();
            subject.fromJson(jsonArray.getString(i));
            subjectList.add(subject);
        }
        return subjectList;
    }

    @Override
    public String submitReply(Reply reply) throws Exception {
        final String methodName = "submitReply";
        final String soapAction = "";

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, reply.toJson());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);
        return callResult.getMessage();
    }

    @Override
    public List<Reply> queryReply(Subject subject, Date lastRefreshDate) throws Exception {
        List<Reply> replyList = new ArrayList<Reply>();
        final String methodName = "queryReply";
        final String soapAction = "";

        /**
         * wrap params as json format
         */
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            jsonObject.put("subjectId", subject.getId());
            jsonObject.put("lastDate", format.format(lastRefreshDate));
        } catch (Exception e) {
            throw new Exception("Error to organize param!");
        }

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, jsonObject.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);

        JSONArray jsonArray = new JSONArray(callResult.getMessage());
        for (int i = 0; i < jsonArray.length(); i++) {
            Reply reply = new Reply();
            reply.fromJson(jsonArray.getString(i));
            replyList.add(reply);
        }
        return replyList;
    }

    @Override
    public String uploadImage(Bitmap bitmap) throws Exception {
        final String methodName = "uploadImage";
        final String soapAction = "";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] fileByte = os.toByteArray();
        String data = new String(Base64.encode(fileByte));

        SoapObject paramSoap = new SoapObject(nameSpace, methodName);
        paramSoap.addProperty(ARGS01, new SoapPrimitive(SoapEnvelope.ENC, "base64Binary", data));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(paramSoap);

        ProcessResult callResult = callWebserviceAndParseResult(soapAction, envelope);

        return callResult.getMessage();
    }

    /**
     * 调用web服务，并解析结果
     * @param soapAction
     * @param envelope
     * @return
     * @throws Exception
     */
    private ProcessResult callWebserviceAndParseResult(final String soapAction, SoapSerializationEnvelope envelope) throws Exception {
        String result = "";
        HttpTransportSE transport = new HttpTransportSE(endPoint, 20000);
        try {
            transport.call(soapAction, envelope);
            SoapObject object = (SoapObject) envelope.bodyIn;
            result = object.getProperty(0).toString();
        } catch (Exception e) {
            throw new Exception("webservice 调用失败");
        }

        /**
         * 解析结果
         */
        ProcessResult callResult = new ProcessResult();
        callResult.fromJson(result);
        if (callResult.getCode() != 0) {
            throw new Exception(callResult.getMessage());
        }

        return callResult;
    }
}
