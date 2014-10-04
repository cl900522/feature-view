package com.clinix.webservice.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.clinix.bean.HistoryRecord;
import com.clinix.bean.ProcessResult;
import com.clinix.bean.Reply;
import com.clinix.bean.Subject;
import com.clinix.bean.Version;
import com.clinix.dao.AskMobileDao;
import com.clinix.dao.impl.AskMobileDaoImpl;
import com.clinix.servlet.AskMobileServlet;
import com.clinix.webservice.AskMobileWebService;

public class AskMobielWebServiceImpl implements AskMobileWebService {
    private AskMobileDao askMobileDao;

    public AskMobielWebServiceImpl() {
        askMobileDao = AskMobileDaoImpl.instance();
    }

    @Override
    public String getLatestVersion() {
        ProcessResult result = new ProcessResult();
        Version version;
        try {
            version = this.askMobileDao.getLatestVersion();
            result.setMessage(version.toJsonString());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String uploadHistoryRecord(String jsonRecords) {
        ProcessResult result = new ProcessResult();
        HistoryRecord record = new HistoryRecord();
        try {
            record.fromJsonString(jsonRecords);
            String serverid = this.askMobileDao.saveHistoryRecord(record);
            result.setMessage(serverid);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String submitSubject(String jsonSubject) {
        Subject question = new Subject();
        ProcessResult result = new ProcessResult();
        try {
            question.fromJsonString(jsonSubject);
            String serverid = this.askMobileDao.saveSubject(question);
            result.setMessage(serverid);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String querySubject(String queryJson) {
        JSONObject jsonObject = JSONObject.fromObject(queryJson);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", jsonObject.getString("userId"));
        paramMap.put("isOver", Boolean.parseBoolean(jsonObject.getString("isOver")));

        ProcessResult result = new ProcessResult();
        List<Subject> subjectList = new ArrayList<Subject>();
        try {
            subjectList = this.askMobileDao.getSubjectList(paramMap);
            JSONArray jsonarray = new JSONArray();
            for (Subject subject : subjectList) {
                jsonarray.add(subject.toJsonString());
            }
            result.setMessage(jsonarray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String submitReply(String replyStr) {
        Reply reply = new Reply();
        ProcessResult result = new ProcessResult();
        try {
            reply.fromJsonString(replyStr);
            String serverid = this.askMobileDao.saveReply(reply);
            result.setMessage(serverid);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String queryReply(String jsonParam) {
        ProcessResult result = new ProcessResult();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("subjectId", jsonObject.getString("subjectId"));
        paramMap.put("lastDate", jsonObject.getString("lastDate"));

        List<Reply> replyList = new ArrayList<Reply>();
        try {
            replyList = this.askMobileDao.getReplyListBySubject(paramMap);
            JSONArray jsonarray = new JSONArray();
            for (Reply reply : replyList) {
                jsonarray.add(reply.toJsonString());
            }
            result.setMessage(jsonarray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result.toString();
    }

    @Override
    public String uploadImage(byte[] imageBinary) {
        ProcessResult result = new ProcessResult();
        OutputStream os = null;
        InputStream is = null;
        try {
            String dirPath = AskMobileServlet.appPath + "image\\";
            File imagePath = new File(dirPath);
            if (!imagePath.exists()) {
                imagePath.mkdir();
            }

            String filePath;
            File imageFile;
            do {
                filePath = dirPath + generateRandomFileName(40) + ".png";
                imageFile = new File(filePath);
            } while (imageFile.exists());

            os = new FileOutputStream(filePath);
            is = new ByteArrayInputStream(imageBinary);

            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            result.setMessage(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMessage("Failt to upload image!");
        } finally {
            /**
             * 关闭流
             */
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }

            } catch (Exception e) {
            }
        }
        return result.toString();
    }

    private String generateRandomFileName(int length) {
        String str = "abcdl012345mnopqrstuvwxyzABCDEFGefghijkHIJKLMNOPQRSTUVWXYZ6789_+-[]{}';";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(71);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
