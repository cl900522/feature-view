package com.clinix.askmobile.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clinix.askmobile.core.bean.Body;
import com.clinix.askmobile.core.bean.BodyPart;
import com.clinix.askmobile.core.bean.HistoryRecord;
import com.clinix.askmobile.core.bean.Depart;
import com.clinix.askmobile.core.bean.ShortIllInfo;
import com.clinix.askmobile.core.bean.ShortSymInfo;
import com.clinix.askmobile.core.bean.IllStruct;
import com.clinix.askmobile.core.bean.SymStruct;

public interface MedicalDao {
    /**
     * 获取表的数据行
     * @param string
     * @return
     */
    public int getTableDataCount(String string);

    /**
     * 获取疾病列表数据
     * @return
     */
    public ArrayList<IllStruct> getIllData();

    /**
     * 获取症状数据列表
     * @return
     */
    public ArrayList<SymStruct> getSymData();

    public ArrayList<ShortSymInfo> getSymListByPartId(Map<String, Object> param);

    public IllStruct getIllInfoStruct(int key_no);

    /**
     * 从数据库获取科室列表
     * @return
     */
    public ArrayList<Depart> getDepartList();

    /**
     * 数据库查询科室的疾病列表
     * @param deptid
     * @return
     */
    public ArrayList<ShortIllInfo> getIllsByDepartId(String deptid);

    /**
     * 查询数据库匹配症状
     * @param paramMap
     * @return
     */
    public ArrayList<ShortSymInfo> searchSymByKeyWord(Map<String, Object> paramMap);

    /**
     * 保存的疾病记录
     * @param paramMap
     * @return
     */
    public void saveFavourite(HistoryRecord his) throws SQLException;

    /**
     * 获取身体部分列表
     * @param sex
     * @return
     */
    public List<BodyPart> getBodyPartList(Body sex);

    /**
     * 存储历史记录，要求每次查看疾病结果时都保存记录
     */
    public void addHistoryRecord(HistoryRecord history) throws Exception;

    /**
     * 获取保存的本地历史记录
     */
    public List<HistoryRecord> getHistoryRecordList(String tag);

    /**
     * 删除记录
     */
    public void updateHistoryRecord(HistoryRecord history) throws Exception;

}
