package com.clinix.askmobile.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;

public interface MedicalSer {

    /**
     * 根据部位编号获取主要症状列表
     * @param body
     * @return
     */
    public ArrayList<ShortSymInfo> getSymListByPartId(Body body);

    /**
     * 获取主症选择列表
     * @param cmdInfo
     * @return
     */
    public List<ShortSymInfo> SymToFB(Body body);

    /**
     * 主症+起病
     * @param cmdInfo
     * @return
     */
    public List<ShortSymInfo> SymBSToZZ(Body body);

    /**
     * 主症+起病+辅症
     * @param cmdInfo
     * @return
     */
    public List<ShortIllInfo> SymTZToILL(Body body);

    /**
     * 回溯
     * @param cmdInfo
     * @return
     */
    public List<ShortSymInfo> IllToSym(Body body);

    /**
     * 根据疾病编号返回疾病信息
     * @param key_no
     * @return
     */
    public IllStruct getIllInfoStruct(int key_no) throws Exception;

    /**
     * 返回部门部门列表
     * @return
     */
    public List<Depart> getDepartList();

    /**
     * 根据部门编号获取疾病列表
     * @param deptid
     * @return
     */
    public List<ShortIllInfo> getIllsByDepartId(String deptid);

    /**
     * 根据keyWOrd和性别查询主要症状列表
     * @param keyWord
     * @param sex
     * @return
     */
    public List<ShortSymInfo> searchSymByKeyWord(String keyWord, Body body);

    /**
     * 保存关注的疾病链接
     * @param url
     * @throws SQLException
     */
    public void saveFavourite(HistoryRecord his) throws SQLException;

    /**
     * 根据上级身体部位或子级身体部位
     */
    public List<BodyPart> getBodyPartList(Body sex);

    /**
     * 存储历史记录，要求每次查看疾病结果时都保存记录。保存成功后会将编号回写记录的本地编号中
     */
    public void addHistoryRecord(HistoryRecord history) throws Exception;

    /**
     * 获取保存的本地历史记录,如果tag为"UPLOAD"则取到需要上传的记录，
     * 如果tag为"WILLING",则获取用户自己主动保存的记录
     */
    public List<HistoryRecord> getHistoryRecordList(String tag);

    /**
     * 删除记录
     */
    public void updateHistoryRecord(HistoryRecord record) throws Exception;
}
