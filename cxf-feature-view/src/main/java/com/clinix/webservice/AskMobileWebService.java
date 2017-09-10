package com.clinix.webservice;

/**
 * webservice的接口类，DataSynchronizationSerProxy是他的实现的远程代理，请在子线程中调用(
 * 且andorid系统是不允许在主线程中调用的)各个方法，因为主线程中调用网络服务会导致主页面卡死
 * @return
 */
public interface AskMobileWebService {
    /**
     * 获取服务端版信息
     * @return in format {"id":12873,"version":"10.3.4Beta","versionName":"Orange","updateInfo":"删除上传数据功能","downloadUrl":"http://bizhi.zhuoku.com/2010/05/11/Ubuntu/1680x1050.png","compatibleVersion":"7.*,8.*,9.*","state":true,"remindFrequency":12,"releaseDate":"2014-09-24"}
     */
    public String getLatestVersion();

    /**
     * HistoryRecord的集合对象记录的Json字符串
     * @param jsonRecord in format {"id":456723,"number":"16237","userId":16237,"sex":"wm","age":12,"zhu":"精神恍惚","qi":"不定时发病","fu":"到处乱跑","ji":"神经病","ke":"精神科","ip":"232.123.1.14","gps":"LAT:123.98132;LON:123.0398123","date":"2014-09-24","agent":"this is tester's devid id"}
     * @return
     */
    public String uploadHistoryRecord(String jsonRecord);

    /**
     * 提交的问题Json字符串
     * @param jsonQuestion in format {"id":11233,"askerId":9283948,"dept":"外科","historyId":928948,"content":"请问大夫如何治疗呢？","tag":"外伤，骨伤","score":12,"readNumber":12873,"submitDate":"2014-09-24","lastReplyDate":"2014-09-24","lastReplyUserId":92833948,"isTemplate":true,"isOver":true,"hasNewReply":true}
     * @return
     */
    public String submitSubject(String jsonQuestion);

    /**
     * @param jsonparam in format {"userId":12873,"isOver","true"}/{"userId":12873,"isOver","false"}
     * @return
     */
    public String querySubject(String jsonparam);

    /**
     * 回复信息的json字符串
     * @param reply in fromat {"id":1123223,"questionId":928374023948,"replyId":928374023948,"content":"请问大夫如何治疗呢？","replyDate":"2014-09-14 10:45:20.763","score":12,"isRead":true}
     * @return
     */
    public String submitReply(String reply);

    /**
     * 查询回复的json字符
     * @param jsonParam in format
     *            {"subjectId":12873,"lastDate":"2014-09-14 10:45:20.763"}
     * @return
     */
    public String queryReply(String jsonParam);

    /**
     * 上传图片
     * @param imageBinary
     * @return
     */
    public String uploadImage(byte[] imageBinary);
}
