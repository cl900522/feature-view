package com.clinix.askmobile.webservice;

import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;

import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.Reply;
import com.clinix.askmobile.core.bean.Subject;
import com.clinix.askmobile.core.bean.UserInfo;
import com.clinix.askmobile.core.bean.VersionInfo;

/**
 * webservice的接口类，DataSynchronizationSerProxy是他的实现的远程代理，请在子线程中调用(
 * 且andorid系统是不允许在主线程中调用的)各个方法，因为主线程中调用网络服务会导致主页面卡死
 * @return
 */
public interface WebServiceProxy {
    /**
     * 获取服务端版信息
     * @return
     */
    public VersionInfo getLatestVersion() throws Exception;

    /**
     * HistoryRecord的集合对象记录的Json字符串
     * @return
     */
    public String uploadHistoryRecord(HistoryRecord history) throws Exception;

    /**
     * 提交咨询的问题
     * @param jsonQuestion
     * @return
     * @throws Exception TODO
     */
    public String submitSubject(Subject question) throws Exception;

    /**
     * 查询用户问题列表，tag表示完成和进行中的标记
     * @param user
     * @param tag
     * @return
     * @throws Exception TODO
     */
    public List<Subject> querySubject(UserInfo user, String tag) throws Exception;

    /**
     * 提交回复
     * @param reply
     * @return
     * @throws Exception TODO
     */
    public String submitReply(Reply reply) throws Exception;

    /**
     * 查询回复的json字符
     * @param subject 问题编号
     * @param lastRefreshDate 从改时间开始之后的
     * @return
     */
    public List<Reply> queryReply(Subject subject, Date lastRefreshDate) throws Exception;

    /**
     * 上传图片到服务器
     * @param imageFile
     * @return
     * @throws Exception
     */
    public String uploadImage(Bitmap bitmap) throws Exception;
}
